package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#GET_GAME_STATE_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class GetGameStateOkAction extends GameAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        Objects.requireNonNull(parameters);
        logger.debug("Displaying the game...");
        Platform.runLater(() -> {
            parseData(parameters);
            Renderer.displayGameScreen(playerOne, playerTwo, playerOnTurn);
        });
    }

}
