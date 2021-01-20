package cz.markovda.connection;

import cz.markovda.connection.vo.Server;
import cz.markovda.connection.vo.SessionInfo;
import cz.markovda.connection.vo.User;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.request.ResponseActionMap;
import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Singleton class for connecting to a server instance.
 *
 * @author David Markov
 * @since 9. 1. 2021
 */
public class Connector {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The only instance of this singleton class.
     */
    private static final Connector INSTANCE = new Connector();

    private static final char PARAM_DELIMITER = '|';
    private static final char STOP_CHAR = '\n';

    /**
     * Socket for connection to remote server.
     */
    private Socket connectionSocket;

    /**
     * Are we connected to server?
     */
    private boolean connected = false;

    private boolean serverUp = false;

    /**
     * Information about current connection session.
     */
    private SessionInfo sessionInfo;
    /**
     * Reactions to responses mapped to response codes.
     */
    private final ResponseActionMap responseReactions;

    /**
     * Returns the single one existing instance of this class.
     *
     * @return the only existing instance of Connector.
     */
    public static Connector getInstance() {
        return INSTANCE;
    }

    private Connector() {
        // private constructor, because of singleton
        responseReactions = new ResponseActionMap();
    }

    /**
     * Connects to given host at given port.
     *
     * @param host host to connect to (address or host name)
     * @param port port to connect to
     * @throws IOException when connection fails
     */
    public void connect(final String host, final int port) throws IOException {
        logger.info("Connecting to server {} on port {}", host, port);
        if (connected) {
            disconnect();
        }

        connectionSocket = new Socket(host, port);

        if (connectionSocket.isConnected()) {
            connected = true;
            sessionInfo = new SessionInfo(new Server(host, port), new User());
            logger.debug("Connected...");

            new Thread(this::listenForResponses).start();
        } else {
            throw new IOException("Error establishing connection to server");
        }

    }

    /**
     * Sends given message to the server we are connected to. If we are not connected to any server,
     * doesn't do anything.
     *
     * @param request request to send
     */
    public void sendRequest(final Request request) {
        if (!connected || request == null) {
            return;
        }

        String message = createMessage(request);
        OutputStream outputStream;
        try {
            outputStream = getStreamToServer();
            if (outputStream != null) {
                logger.debug("Sending message: {}", message);
                outputStream.write(message.getBytes());
            }
        } catch (IOException e) {
            logger.error("Error while sending message {}", message, e);
        }
    }

    /**
     * If connected to server, closes all open sockets and streams.
     */
    public void disconnect() {
        if (!connected) {
            return;
        }

        try {
            logger.info("Disconnecting from {}:{}", sessionInfo.getServer().getAddress(), sessionInfo.getServer().getPort());
            connectionSocket.close();
            connected = false;
            connectionSocket = null;
            sessionInfo.setServer(null);
            sessionInfo.setUser(null);
        } catch (IOException e) {
            logger.error("Error closing server connection socket!", e);
        }
    }

    public void setServerUp(final boolean serverUp) {
        this.serverUp = serverUp;
    }

    /**
     * Returns information about current connection session.
     *
     * @return session info
     */
    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    /**
     * Returns output stream to the server, if we are connected. The returned stream has to be closed manually.
     */
    private OutputStream getStreamToServer() {
        if (!connected) {
            return null;
        }

        OutputStream outputStream;
        try {
            outputStream = connectionSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Error while getting socket output stream!");
            e.printStackTrace();
            return null;
        }

        return outputStream;
    }

    /**
     * Returns input stream from the server, if we are connected. The returned stream has to be closed manually.
     */
    private InputStream getStreamFromServer() {
        if (!connected) {
            return null;
        }

        InputStream inputStream;
        try {
            inputStream = connectionSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Error while getting socket input stream!");
            e.printStackTrace();
            return null;
        }

        return inputStream;
    }

    private String createMessage(final Request request) {
        if (request == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(String.valueOf(sessionInfo.getUser().getUserId()))
                .append(PARAM_DELIMITER)
                .append(request.getRequestType().name());

        for (String param : request.getParameters()) {
            sb.append(PARAM_DELIMITER)
                    .append(param);
        }
        sb.append(STOP_CHAR);

        return sb.toString();
    }

    private void listenForResponses() {
        startPingThread();
        InputStream inputStream = getStreamFromServer();
        if (inputStream == null) {
            return;
        }

        int availableBytes;
        while (!connectionSocket.isClosed()) {
            try {
                availableBytes = inputStream.available();
                if (availableBytes > 0) {
                    byte[] buffer = inputStream.readNBytes(availableBytes);
                    String message = new String(buffer, StandardCharsets.UTF_8);
                    logger.debug("Incomming message: {}", message);
                    processResponse(message.substring(0, message.length() - 1));
                }
            } catch (IOException e) {
                logger.error("Error while reading server responses", e);
                Platform.runLater(() -> {
                    Renderer.showConfirmationWindow("Connection error! Shutting down...");
                    Platform.exit();
                });
                System.exit(1);
            }
        }
    }

    private void processResponse(final String response) {
        String[] tokens = response.split("\\|");
        int code = Integer.parseInt(tokens[0]);

        responseReactions.get(code).execute(Arrays.copyOfRange(tokens, 1, tokens.length));

    }

    private void startPingThread() {
        new Thread(() -> {
            while (true) {
                if (connected) {
                    sendRequest(new Request(RequestType.PING));

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        logger.error("Ping thread was unexpectedly waken up!", e);
                    }

                    if (serverUp) {
                        serverUp = false;
                    } else {
                        logger.error("Connection to the server lost! Shutting down...");
                        Platform.runLater(() -> {
                            Renderer.showConfirmationWindow("Connection lost! Shutting down...");
                            Platform.exit();
                        });
                        System.exit(1);
                    }
                }
            }
        }).start();
    }


}
