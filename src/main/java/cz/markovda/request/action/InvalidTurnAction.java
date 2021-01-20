package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#INVALID_TURN} server response.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class InvalidTurnAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.GAME_SCREEN) {
            Platform.runLater(() -> Renderer.showInformationWindow("Invalid turn! Try again."));
        }

        logger.error("Invalid turn was sent to the server!");
    }
}
