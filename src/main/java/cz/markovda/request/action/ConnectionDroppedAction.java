package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#CONNECTION_DROPPED} server response.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class ConnectionDroppedAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        logger.error("Connection to server lost! Shutting down...");
        Platform.runLater(() -> {
            Renderer.showConfirmationWindow("ERROR! Connection lost!");
            Platform.exit();
            System.exit(1);
        });
    }
}
