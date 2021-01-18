package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#GAME_DELETED} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class GameDeletedAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.LOBBY) {
            logger.debug("Some game was deleted!");
            Platform.runLater(() -> Renderer.removeLobbyGame(parameters[0]));
        }
    }
}
