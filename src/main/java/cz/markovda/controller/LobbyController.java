package cz.markovda.controller;


import cz.markovda.connection.Connector;
import cz.markovda.game.LobbyGame;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {

    private final List<LobbyGame> games;

    private final List<Label> labels;

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

    /*
     * ---------- CONTROLLER METHODS ----------
     */

    public LobbyController() {
        games = new ArrayList<>();
        labels = new ArrayList<>();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePanel1.setOpacity(0);
        gamePanel1.setDisable(true);
        gamePanel2.setOpacity(0);
        gamePanel2.setDisable(true);
        gamePanel3.setOpacity(0);
        gamePanel3.setDisable(true);
        gamePanel4.setOpacity(0);
        gamePanel4.setDisable(true);
        gamePanel5.setOpacity(0);
        gamePanel5.setDisable(true);
        gamePanel6.setOpacity(0);
        gamePanel6.setDisable(true);

        labels.add(player1);
        labels.add(player2);
        labels.add(player3);
        labels.add(player4);
        labels.add(player5);
        labels.add(player6);
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

    public void addGames(final List<LobbyGame> newGames) {
        newGames.forEach(this::addGame);
    }

    public void addGame(final LobbyGame newGame) {
        if (newGame == null || newGame.getOpponentNick() == null || newGame.getOpponentNick().isEmpty()) {
            return;
        }

        games.add(newGame);
        if (games.size() > labels.size()) {
            return;
        }

        for (Label label : labels) {
            if (label.getParent().getOpacity() == 0.0) {
                label.setText(newGame.getOpponentNick());
                label.getParent().setOpacity(1);
                label.getParent().setDisable(false);
                break;
            }
        }
    }

    @FXML
    private void joinGame(ActionEvent event) {
        Parent gamePanel = ((Button)event.getSource()).getParent();
        Label playerNick = (Label) gamePanel.getChildrenUnmodifiable().stream()
                                .filter((node) -> node instanceof Label && !((Label)node).getText().contains("player"))
                                .findFirst().get();
        System.out.println("Player nick: " + playerNick);
    }
}
