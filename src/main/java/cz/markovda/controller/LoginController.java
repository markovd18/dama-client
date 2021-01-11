package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.request.RequestFactory;
import cz.markovda.view.Renderer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @FXML
    private TextField nicknameInput;

    public void loginToServer() {
        //TODO markovda create appropriate login request
        String nickname = nicknameInput.getText();
        logger.info("Logging in with nickname {}", nickname);

        String loginRequest = RequestFactory.createLoginRequest(nickname);
        if (loginRequest == null) {
            Renderer.showInformationWindow("Please fill your nickname!");
            return;
        }

        Connector.getInstance().sendMessage(loginRequest);

    }

    @FXML
    private void switchToConnectionScreen() {
        Renderer.displayConnectionScreen();
    }
}