package cz.markovda.controller;


import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label serverLabel;

    /*
     * ---------- GAME PANELS ----------
     */

    @FXML
    private AnchorPane gamePanel1;

    @FXML
    private AnchorPane gamePanel2;

    @FXML
    private AnchorPane gamePanel3;

    @FXML
    private AnchorPane gamePanel4;

    @FXML
    private AnchorPane gamePanel5;

    @FXML
    private AnchorPane gamePanel6;

    /*
     * ---------- PLAYER LABELS ----------
     */

    @FXML
    private Label player1;

    @FXML
    private Label player2;

    @FXML
    private Label player3;

    @FXML
    private Label player4;

    @FXML
    private Label player5;

    @FXML
    private Label player6;

    /*
     * ---------- JOIN BUTTONS ----------
     */

    @FXML
    private Button joinBtn1;

    @FXML
    private Button joinBtn2;

    @FXML
    private Button joinBtn3;

    @FXML
    private Button joinBtn4;

    @FXML
    private Button joinBtn5;

    @FXML
    private Button joinBtn6;

    /*
     * ---------- CONTROLLER METHODS ----------
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePanel1.setOpacity(0);
        gamePanel2.setOpacity(0);
        gamePanel3.setOpacity(0);
        gamePanel4.setOpacity(0);
        gamePanel5.setOpacity(0);
        gamePanel6.setOpacity(0);
    }

    public void createGame () {
        Connector.getInstance().sendRequest(new Request(RequestType.CREATE_GAME));
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
