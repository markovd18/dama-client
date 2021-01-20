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

import java.util.regex.Pattern;

public class LoginController extends ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @FXML
    private TextField nicknameInput;

    @FXML
    private Label serverInfoLabel;

    public void loginToServer() {
        String nickname = nicknameInput.getText();
        logger.info("Logging in with nickname {}", nickname);

        if (nickname == null || nickname.isEmpty() ||
                !Pattern.matches("[a-zA-Z0-9]*", nickname) || nickname.length() > 20) {
            Renderer.showInformationWindow("Invalid nickname! Valid characters a-zA-Z0-9");
            return;
        }
        Request loginRequest = RequestFactory.createLoginRequest(nickname);

        Connector.getInstance().sendRequest(loginRequest);
        /// If the nickname is invalid, will be removed by the connector later
        Connector.getInstance().getSessionInfo().getUser().setNickname(nickname);
    }

    @FXML
    public void setServerInfo(final String address, final String port) {
        serverInfoLabel.setText(address + ':' + port);
    }

    @FXML
    private void switchToConnectionScreen() {
        Connector.getInstance().disconnect();
        Renderer.displayConnectionScreen();
    }
}