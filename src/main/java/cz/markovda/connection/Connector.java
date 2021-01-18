package cz.markovda.connection;

import cz.markovda.connection.vo.Server;
import cz.markovda.connection.vo.SessionInfo;
import cz.markovda.connection.vo.User;
import cz.markovda.request.Request;
import cz.markovda.request.ResponseActionMap;
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

    /**
     * Are we connected to the server?
     *
     * @return true if connected, otherwise false
     */
    public boolean isConnected() {
        return connected;
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
            }
        }
    }

    private void processResponse(final String response) {
        String[] tokens = response.split("\\|");
        int code = Integer.parseInt(tokens[0]);

        responseReactions.get(code).execute(Arrays.copyOfRange(tokens, 1, tokens.length));
        /*if (code == Response.NEW_CONNECTION_OK.getCode()) {
            if (Renderer.getDisplayedWindow() == Window.CONNECTION_SCREEN) {
                sessionInfo.getUser().setUserId(Integer.parseInt(tokens[1]));
                sendRequest(new Request(RequestType.GET_GAMES));
            }
        } else if (code == Response.RECONNECT_OK.getCode()) {

            //TODO markovda when user was in game, we have to display the game
        } else if (code == Response.CONNECT_INVALID_NICK.getCode()) {
            if (Renderer.getDisplayedWindow() == Window.LOGIN_SCREEN) {
                sessionInfo.getUser().setUserId(0);
                sessionInfo.getUser().setNickname(null);
            }
        } else if (code == Response.CONNECT_INVALID_ID.getCode()) {
            // can it even happen??
            logger.warn("Invalid ID was sent to the server!");
        } else if (code == Response.CANNOT_RECONNECT.getCode()) {
            // can it eve happen?
            logger.warn("Invalid request to reconnect was sent!");
        } else if (code == Response.LOGOUT_OK.getCode()) {
            if (Renderer.getDisplayedWindow() == Window.LOBBY) {
                sessionInfo.getUser().setUserId(0);
                sessionInfo.getUser().setNickname(null);
                Platform.runLater(() -> Renderer.displayLoginScreen(
                        sessionInfo.getServer().getAddress(), String.valueOf(sessionInfo.getServer().getPort())));
            }

        } else if (code == Response.LOGOUT_INVALID_USER.getCode()) {
            // can it even happen??
            logger.warn("Invalid ID sent during logout attempt");
        } else if (code == Response.CANNOT_LOGOUT.getCode()) {
            // can it even happen?
            logger.warn("Requirements for logging out not met!");
        } else if (code == Response.GET_GAMES_OK.getCode()) {
            if (Renderer.getDisplayedWindow() == Window.LOGIN_SCREEN) {
                Platform.runLater(() -> {
                    List<LobbyGame> games = new ArrayList<>();
                    for (int i = 1; i < tokens.length; i++) {
                        games.add(new LobbyGame(tokens[i]));
                    }

                    String serverInfo = sessionInfo.getServer().getAddress() + ':' +
                            sessionInfo.getServer().getPort();
                    Renderer.displayLobby(serverInfo, sessionInfo.getUser().getNickname(), games);
                });
            }
        } else if (code == Response.GET_GAMES_FAIL.getCode()) {
            // can it even happen?
            logger.error("Cannot retrieve games list! Invalid player state.");
        } else if (code == Response.CREATE_GAME_OK.getCode()) {
            if (Renderer.getDisplayedWindow() == Window.LOBBY) {
                Platform.runLater(Renderer::displayLoadingScreen);
            }
        } else if (code == Response.CREATE_GAME_FAIL_STATE.getCode()) {
            // can it even happen?
            logger.error("Cannot create new game. Player not in lobby!");
            Platform.runLater(() -> Renderer.showInformationWindow("Error while creating new game! See logs..."));
        } else if (code == Response.CREATE_GAME_FAIL_ID.getCode()) {
            // can it even happen?
            logger.error("Cannot create new game. Player not logged in!");
            Platform.runLater(() -> Renderer.showInformationWindow("Error while creating new game! See logs..."));
        } else if (code == Response.EXIT_GAME_OK.getCode()) {
            switch (Renderer.getDisplayedWindow()) {
                case LOADING_SCREEN:
                case GAME_SCREEN:
                    sendRequest(new Request(RequestType.GET_GAMES));
                    break;
            }
        } else if (code == Response.EXIT_GAME_FAIL_ID.getCode()) {
            // can it even happen?
            logger.error("Cannot exit game when not logged in!");
            Platform.runLater(() -> Renderer.showInformationWindow("Error while exiting the game! See logs..."));
        } else if (code == Response.EXIT_GAME_FAIL_STATE.getCode()) {
            logger.error("Cannot exit game when not in a game!");
            Platform.runLater(() -> Renderer.showInformationWindow("Error while exiting the game! See logs..."));
        } else if (code == Response.NEW_GAME.getCode()) {
            logger.debug("New game created!");
            Platform.runLater(() -> Renderer.addLobbyGame(new LobbyGame(tokens[1])));
        } else if (code == Response.GAME_DELETED.getCode()) {
            logger.debug("Some game was deleted!");
            Platform.runLater(() -> Renderer.removeLobbyGame(tokens[1]));
        } else if (code == Response.JOIN_GAME_OK.getCode()) {
            if (Renderer.getDisplayedWindow() == Window.LOBBY) {
                sendRequest(new Request(RequestType.GET_GAME_STATE));
            }
        } else if (code == Response.JOIN_GAME_FAIL_ID.getCode()) {

        } else if (code == Response.JOIN_GAME_FAIL_STATE.getCode()) {

        } else if (code == Response.JOIN_GAME_FAIL_NICK.getCode()) {

        } else if (code == Response.GET_GAME_STATE_OK.getCode()) {
            if (Renderer.getDisplayedWindow() == Window.LOBBY) {
                Platform.runLater(() -> {
                    String[] playerOne = tokens[1].split(",");
                    String[] playerTwo = tokens[2].split(",");

                    String playerOneNick = playerOne[0];
                    String playerTwoNick = playerTwo[0];

                    List<GameToken> playerOneTokens = new ArrayList<>();
                    List<GameToken> playerTwoTokens = new ArrayList<>();

                    //TODO markovda create player's token list and display game

                });
            }
        } else if (code == Response.GET_GAME_STATE_FAIL_ID.getCode()) {

        } else if (code == Response.GET_GAME_STATE_FAIL_STATE.getCode()) {

        }
//        } else {
//            logger.warn("Unrecognized response: {}", response);
//        }*/

    }


}
