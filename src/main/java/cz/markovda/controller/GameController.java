package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.game.GameToken;
import cz.markovda.game.Player;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import cz.markovda.view.vo.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class GameController implements Initializable {

    private Player playerOne;
    private Player playerTwo;

    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private Label onTurnLabel;

    @FXML
    private TilePane table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < table.getPrefColumns(); i++) {
            for (int j = 0; j < table.getPrefRows(); j++) {
                table.getChildren().add(new Tile(i, j));
            }
        }
    }

    @FXML
    private void surrender() {
        if (Renderer.showConfirmationWindow("Are you sure you want to surrender and exit?") == ButtonType.YES) {
            Connector.getInstance().sendRequest(new Request(RequestType.EXIT_GAME));
        }
    }

    public void setPlayers(final Player playerOne, final Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        player1Label.setText(this.playerOne.getNickname());
        player1Label.setTextFill(Color.BLUE);
        player2Label.setText(this.playerTwo.getNickname());
        player2Label.setTextFill(Color.RED);
        onTurnLabel.setText(playerOne.getNickname());
        displayTokens();
    }

    private void displayTokens() {
        setTokens(playerOne.getTokens(), GameToken.BLUE_TOKEN);
        setTokens(playerTwo.getTokens(), GameToken.RED_TOKEN);
    }

    private void setTokens(final List<GameToken> tokenList, final String imagePath) {
        for (GameToken token : tokenList) {
            for (Node tile : table.getChildren()) {
                if (((Tile) tile).getPositionX() == token.getPositionX() &&
                        ((Tile) tile).getPositionY() == token.getPositionY()) {
                    ImageView image = new ImageView(imagePath);
                    image.setFitHeight(Tile.SIZE);
                    image.setFitWidth(Tile.SIZE);
                    ((Tile) tile).getChildren().add(image);
                    break;
                }
            }
        }
    }
}
