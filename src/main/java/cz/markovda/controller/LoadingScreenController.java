package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import javafx.fxml.FXML;

public class LoadingScreenController {

    @FXML
    private void exitGame() {
        Connector.getInstance().sendRequest(new Request(RequestType.EXIT_GAME));
    }
}
