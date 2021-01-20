package cz.markovda.request.action;

import cz.markovda.game.GameToken;
import cz.markovda.game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class representing reaction to changing of the game state.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public abstract class GameAction{

    protected Player playerOne;
    protected Player playerTwo;
    protected String playerOnTurn;

    protected void parseData(final String[] parameters) {
        Objects.requireNonNull(parameters);
        String[] playerOneData = parameters[0].split(",");
        String[] playerTwoData = parameters[2].split(",");
        String playerOneNick = playerOneData[0];
        String playerTwoNick = playerTwoData[0];

        List<GameToken> playerOneTokens =
                createTokenList(Arrays.copyOfRange(playerOneData, 1, playerOneData.length));
        List<GameToken> playerOneDraughts =
                createTokenList(parameters[1].split(","));
        List<GameToken> playerTwoTokens =
                createTokenList(Arrays.copyOfRange(playerTwoData, 1, playerTwoData.length));
        List<GameToken> playerTwoDraughts =
                createTokenList(parameters[3].split(","));

        playerOne = new Player(playerOneNick, playerOneTokens, playerOneDraughts);
        playerTwo = new Player(playerTwoNick, playerTwoTokens, playerTwoDraughts);
        playerOnTurn = parameters[4];
    }

    private List<GameToken> createTokenList(final String[] tokenArray) {
        List<GameToken> gameTokens = new ArrayList<>();

        int positionX = 0;
        int positionY;
        for (int i = 0; i < tokenArray.length; i++) {
            if (tokenArray[i].isEmpty()) {
                break;
            }
            if (i % 2 == 0) {
                positionX = Integer.parseInt(tokenArray[i]);
            } else {
                positionY = Integer.parseInt(tokenArray[i]);
                gameTokens.add(new GameToken(positionX, positionY));
            }
        }

        return gameTokens;
    }
}
