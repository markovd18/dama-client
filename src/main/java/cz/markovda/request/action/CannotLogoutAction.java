package cz.markovda.request.action;

import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#CANNOT_LOGOUT} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class CannotLogoutAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        // this should never happen
        Platform.runLater(() -> Renderer.showInformationWindow("Only players in lobby may request to logout!"));
        logger.error("Only players in lobby may request to logout!");
    }
}
