package cz.markovda.request.action;

import cz.markovda.game.GameToken;
import cz.markovda.game.Player;
import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#GET_GAME_STATE_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class GetGameStateOkAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void execute(String[] parameters) {
        Objects.requireNonNull(parameters);
        logger.debug("Displaying new game...");
        Platform.runLater(() -> {
            String[] playerOneData = parameters[0].split(",");
            String[] playerTwoData = parameters[1].split(",");
            String playerOneNick = playerOneData[0];
            String playerTwoNick = playerTwoData[0];

            List<GameToken> playerOneTokens =
                    createTokenList(Arrays.copyOfRange(playerOneData, 1, playerOneData.length));
            List<GameToken> playerTwoTokens =
                    createTokenList(Arrays.copyOfRange(playerTwoData, 1, playerTwoData.length));

            Player playerOne = new Player(playerOneNick, playerOneTokens);
            Player playerTwo = new Player(playerTwoNick, playerTwoTokens);

            Renderer.displayGameScreen(playerOne, playerTwo);
        });
    }

    private List<GameToken> createTokenList(final String[] tokenArray) {
        List<GameToken> gameTokens = new ArrayList<>();

        int positionX = 0;
        int positionY;
        for (int i = 0; i < tokenArray.length; i++) {
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
