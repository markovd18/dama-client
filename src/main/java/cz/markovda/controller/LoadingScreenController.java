package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;

public class LoadingScreenController {

    @FXML
    private void exitGame() {
        if (Renderer.showConfirmationWindow("Do you really want to leave the game?") == ButtonType.YES) {
            Connector.getInstance().sendRequest(new Request(RequestType.EXIT_GAME));
        }
    }
}
