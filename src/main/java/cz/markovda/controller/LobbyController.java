package cz.markovda.controller;


import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyController extends ApplicationController {

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label serverLabel;

    public void createGame () {

    }

    public void logout() {
        Connector.getInstance().sendRequest(new Request(RequestType.LOGOUT));
    }

    @FXML
    public void setInfoLabels(final String serverInfo, final String nickname) {
        serverLabel.setText(serverInfo);
        nicknameLabel.setText(nickname);
    }
}
