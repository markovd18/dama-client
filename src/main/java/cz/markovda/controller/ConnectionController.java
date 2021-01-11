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
        try {
            Connector.getInstance().connect(addressInput.getText(), Integer.parseInt(portInput.getText()));
            switchToLoginScreen();

            //TODO markovda create thread for reading info from server
        } catch (IOException e) {
            logger.error("Error while establishing connection", e);
            Renderer.showInformationWindow("Error establishing connection to the server!");
        }
    }

    @FXML
    private void switchToLoginScreen() throws IOException {
        Renderer.displayLoginScreen();
    }
}
