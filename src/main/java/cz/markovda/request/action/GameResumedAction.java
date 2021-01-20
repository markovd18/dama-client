package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#GAME_RESUMED} server response.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class GameResumedAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        logger.info("Opponent reconnected. Resuming game...");
        Connector.getInstance().sendRequest(new Request(RequestType.GET_GAME_STATE));
    }
}
