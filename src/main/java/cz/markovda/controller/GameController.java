package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.game.GameToken;
import cz.markovda.game.Player;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import cz.markovda.view.vo.Tile;
import cz.markovda.view.vo.TileClickedEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class GameController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    private Tile selectedTile;

    private boolean onTurn;

    private String myNickname = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < table.getPrefColumns(); i++) {
            for (int j = 0; j < table.getPrefRows(); j++) {
                table.getChildren().add(new Tile(j, i));
            }
        }

        table.addEventHandler(TileClickedEvent.TILE_CLICKED, (event) -> {
            if (!onTurn) {
                return;
            }

            if (selectedTile == null) {
                selectedTile = (Tile) event.getTarget();
                if (selectedTile.getChildren().isEmpty()) {
                    selectedTile = null;
                } else {
                    boolean iAmPlayerOne = player1Label.getText().equals(myNickname);
                    if (iAmPlayerOne) {
                        if (((ImageView) selectedTile.getChildren().get(0)).getImage().getUrl().contains(GameToken.BLUE_TOKEN)) {
                            selectedTile.setBorder(new Border(new BorderStroke(Color.LIGHTGREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
                        }
                    } else {
                        if (((ImageView) selectedTile.getChildren().get(0)).getImage().getUrl().contains(GameToken.RED_TOKEN)) {
                            selectedTile.setBorder(new Border(new BorderStroke(Color.LIGHTGREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
                        }
                    }
                }
            } else {
                if (!selectedTile.equals(event.getTarget())) {
                    if (isValidTurn(selectedTile, event)) {
                        Request turnRequest = new Request(RequestType.TURN,
                                "" + selectedTile.getPositionX() + "," + selectedTile.getPositionY(),
                                "" + event.getX() + "," + event.getY());
                        Connector.getInstance().sendRequest(turnRequest);
                    }
                }

                selectedTile.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                selectedTile = null;
            }
        });
    }

    @FXML
    private void surrender() {
        if (Renderer.showConfirmationWindow("Are you sure you want to surrender and exit?") == ButtonType.YES) {
            Connector.getInstance().sendRequest(new Request(RequestType.EXIT_GAME));
        }
    }

    public void setPlayers(final Player playerOne, final Player playerTwo, final String playerOnTurn) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        player1Label.setText(this.playerOne.getNickname());
        player1Label.setTextFill(Color.LIGHTBLUE);
        player2Label.setText(this.playerTwo.getNickname());
        player2Label.setTextFill(Color.RED);
        onTurnLabel.setText(playerOnTurn);
        myNickname = Connector.getInstance().getSessionInfo().getUser().getNickname();
        onTurn = playerOnTurn.equals(myNickname);
        displayTokens();
    }

    public void updateState(final Player playerOne, final Player playerTwo, final String playerOnTurn) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        onTurnLabel.setText(playerOnTurn);
        onTurn = playerOnTurn.equals(myNickname);
        updateTokens();
    }

    private void displayTokens() {
        setTokens(playerOne.getTokens(), GameToken.BLUE_TOKEN);
        setTokens(playerTwo.getTokens(), GameToken.RED_TOKEN);
        displayDraughts(playerOne.getDraughts(), Color.LIGHTBLUE);
        displayDraughts(playerTwo.getDraughts(), Color.RED);
    }

    private void displayDraughts(final List<GameToken> draughts, final Color color) {
        for (GameToken draught : draughts) {
            for (Node tile : table.getChildren()) {
                if (((Tile) tile).getPositionX() == draught.getPositionX() &&
                        ((Tile) tile).getPositionY() == draught.getPositionY()) {
                    ((Tile) tile).setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }
    }

    private void updateTokens() {
        for (Node tile : table.getChildren()) {
            ((Tile)tile).getChildren().clear();
            if (isDraughtTile((Tile) tile)) {
                ((Tile)tile).setBackground(new Background(new BackgroundFill(Color.rgb(60, 63, 66), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }

        displayTokens();
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

    private boolean isValidTurn(final Tile selectedTile, final TileClickedEvent event) {
        /// Checking move backwards
        if (isDraught(selectedTile)) {
            if (selectedTile.getPositionX() == event.getX() || selectedTile.getPositionY() == event.getY()) {
                return false;
            }
        } else {
            if (myNickname.equals(player1Label.getText()) && (selectedTile.getPositionX() == event.getX() || event.getY() <= selectedTile.getPositionY())) {
                return false;
            }
            if (myNickname.equals(player2Label.getText()) && (selectedTile.getPositionX() == event.getX() || event.getY() >= selectedTile.getPositionY())) {
                return false;
            }
        }
        /// Checking diagonal move, allowed by 1 and 2 only
        if (Math.abs(selectedTile.getPositionX() - event.getX()) != Math.abs(selectedTile.getPositionY() - event.getY())) {
            return false;
        }
        if (!isDraught(selectedTile) && (Math.abs(selectedTile.getPositionX() - event.getX()) > 2 || Math.abs(selectedTile.getPositionY() - event.getY()) > 2)) {
            return false;
        }
        return ((Tile) event.getTarget()).getChildren().isEmpty();
    }

    private boolean isDraught(final Tile selectedTile) {
        List<GameToken> draughts = myNickname.equals(player1Label.getText()) ? playerOne.getDraughts() : playerTwo.getDraughts();
        for (GameToken draught : draughts) {
            if (draught.getPositionX() == selectedTile.getPositionX() &&
                    draught.getPositionY() == selectedTile.getPositionY()) {
                return true;
            }
        }

        return false;
    }

    private boolean isDraughtTile(final Tile tile) {
        if (tile.getBackground().getFills().get(0).getFill().equals(Color.RED) ||
                tile.getBackground().getFills().get(0).getFill().equals(Color.LIGHTBLUE)) {
            return true;
        }

        return false;
    }
}
