package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#CREATE_GAME_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class CreateGameOkAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.LOBBY) {
            logger.debug("New game created, displaying loading screen...");
            Platform.runLater(Renderer::displayLoadingScreen);
        }
    }
}
