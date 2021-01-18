package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#GAME_STARTED} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class GameStartedAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.LOADING_SCREEN) {
            logger.info("Game started, requesting state info...");
            Connector.getInstance().sendRequest(new Request(RequestType.GET_GAME_STATE));
        }
    }
}
