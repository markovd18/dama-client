package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#EXIT_GAME_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class ExitGameOkAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        logger.debug("Game exited, returning to lobby...");
        switch (Renderer.getDisplayedWindow()) {
            case LOADING_SCREEN:
            case GAME_SCREEN:
                Connector.getInstance().sendRequest(new Request(RequestType.GET_GAMES));
                break;
        }
    }
}
