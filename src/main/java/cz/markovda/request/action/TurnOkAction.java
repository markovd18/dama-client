package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import javafx.application.Platform;

import java.util.Objects;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#TURN_OK} server response.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class TurnOkAction extends GameAction implements ICommand {

    @Override
    public void execute(String[] parameters) {
        Objects.requireNonNull(parameters);
        Platform.runLater(() -> {
            parseData(parameters);
            Renderer.updateGameScreen(playerOne, playerTwo, playerOnTurn);
        });
    }
}
