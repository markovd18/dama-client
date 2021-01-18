package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#CONNECT_INVALID_ID} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class ConnectInvalidIdAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        // this should never happen
        logger.error("Invalid ID was sent to the server while trying to connect!");
        Platform.runLater(() ->
                Renderer.showInformationWindow("Invalid ID sent to the server during connection!"));
    }
}
