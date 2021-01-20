package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#INVALID_STATE} server response.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class InvalidStateAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        logger.error("Application in invalid state!");
        Platform.runLater(() -> Renderer.showInformationWindow("ERROR! Invalid state of application!"));
    }
}
