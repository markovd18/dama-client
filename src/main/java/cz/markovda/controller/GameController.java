package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;


public class GameController {


    @FXML
    private void surrender() {
        if (Renderer.showConfirmationWindow("Are you sure you want to surrender and exit?") == ButtonType.YES) {
            Connector.getInstance().sendRequest(new Request(RequestType.EXIT_GAME));
        }
    }

}
