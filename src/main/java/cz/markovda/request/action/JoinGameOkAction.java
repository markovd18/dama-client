package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#JOIN_GAME_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class JoinGameOkAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.LOBBY) {
            logger.debug("Joining a game...");
            Connector.getInstance().sendRequest(new Request(RequestType.GET_GAME_STATE));
        }
    }
}
