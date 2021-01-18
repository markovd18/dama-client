package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#LOGOUT_INVALID_USER} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class LogoutInvalidUserAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.LOBBY) {
            Platform.runLater(() -> Renderer.showInformationWindow("Invalid ID sent during logout request!"));
        }

        logger.error("Invalid ID sent to server during logout request!");
    }
}
