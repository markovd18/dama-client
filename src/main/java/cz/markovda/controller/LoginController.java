package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestFactory;
import cz.markovda.view.Renderer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @FXML
    private TextField nicknameInput;

    @FXML
    private Label serverInfoLabel;

    public void loginToServer() {
        String nickname = nicknameInput.getText();
        logger.info("Logging in with nickname {}", nickname);

        Request loginRequest = RequestFactory.createLoginRequest(nickname);
        if (loginRequest == null) {
            Renderer.showInformationWindow("Please fill your nickname!");
            return;
        }

        Connector.getInstance().sendRequest(loginRequest);
        //TODO markovda display "progressbar" and wait for confirmation message

    }

    @FXML
    public void setServerInfo(final String address, final String port) {
        serverInfoLabel.setText(address + ':' + port);
        serverInfoLabel.setOpacity(1);
        //TODO markovda label doesn't change when window is rendered
    }

    @FXML
    private void switchToConnectionScreen() {
        Connector.getInstance().disconnect();
        Renderer.displayConnectionScreen();
    }
}