package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#INVALID_ID} server response.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class InvalidIdAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        logger.error("Invalid ID was sent to the server!");
        Platform.runLater(() -> Renderer.showInformationWindow("ERROR! Invalid ID was sent to the server!"));
    }
}
