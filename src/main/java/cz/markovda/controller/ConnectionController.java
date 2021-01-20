package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.view.Renderer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller for the connection screen.
 *
 * @author David Markov
 * @since 4. 11. 2020
 */
public class ConnectionController extends ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @FXML
    private TextField addressInput;

    @FXML
    private TextField portInput;

    /**
     * Connects to server instance with address and port given from input fields in the connection screen.
     */
    public void connectToServer() {
        if (addressInput.getText().isEmpty()) {
            Renderer.showInformationWindow("Please fill the server address!");
        } else if (portInput.getText().isEmpty()) {
            Renderer.showInformationWindow("Please fill the server port!");
        } else {

            int port;
            try {
                port = Integer.parseInt(portInput.getText());
            } catch (Exception e) {
                Renderer.showInformationWindow("Invalid port!");
                return;
            }

            if (!isPortFree(port)) {
                Renderer.showInformationWindow("Invalid port!");
                return;
            }
            try {
                Connector.getInstance().connect(addressInput.getText(), port);
                switchToLoginScreen();
            } catch (IOException e) {
                logger.error("Error while establishing connection", e);
                Renderer.showInformationWindow("Error establishing connection to the server!");
            }
        }
    }

    private boolean isPortFree(final int port) {
        switch (port) {
            case 20:
            case 21:
            case 22:
            case 25:
            case 53:
            case 80:
            case 110:
            case 443:
                return false;
            default:
                if (port < 0 ||port > 65535) {
                    return false;
                }
                return true;
        }
    }

    @FXML
    private void switchToLoginScreen() throws IOException {
        Renderer.displayLoginScreen(addressInput.getText(), portInput.getText());
    }
}
