package cz.markovda.game;

/**
 * Class representing a token in a game of Draughts.
 *
 * @author David Markov
 * @since 17. 1. 2021
 */
public class GameToken {

    private int positionX;
    private int positionY;

    public GameToken(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
