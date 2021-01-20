package cz.markovda.view.vo;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class representing one tile on the game board.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class Tile extends Pane {

    public static final int SIZE = 110;

    private final int positionX;
    private final int positionY;

    public Tile(int positionX, int positionY) {
        setPrefSize(SIZE, SIZE);

        this.positionX = positionX;
        this.positionY = positionY;

        Color baseColor = (positionX + positionY) % 2 == 0 ? Color.WHITE : Color.rgb(60, 63, 66);
        setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));

        setOnMouseClicked((event) -> fireEvent(new TileClickedEvent(TileClickedEvent.TILE_CLICKED, positionX, positionY)));
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
