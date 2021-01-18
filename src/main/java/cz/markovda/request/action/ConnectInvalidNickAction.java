package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#CONNECT_INVALID_NICK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class ConnectInvalidNickAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.LOGIN_SCREEN) {
            logger.error("Invalid nickname sent to server while logging in!");
            Connector.getInstance().getSessionInfo().getUser().setUserId(0);
            Connector.getInstance().getSessionInfo().getUser().setNickname(null);
            Platform.runLater(() ->
                    Renderer.showInformationWindow("Invalid nickname! Allowed characters: a-zA-Z0-9"));
        }
    }
}
