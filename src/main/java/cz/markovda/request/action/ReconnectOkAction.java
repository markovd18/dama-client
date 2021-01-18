package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#RECONNECT_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class ReconnectOkAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        Objects.requireNonNull(parameters);
        Connector.getInstance().getSessionInfo().getUser().setUserId(Integer.parseInt(parameters[0]));
        logger.info("Reconnecting into the game...");
        Connector.getInstance().sendRequest(new Request(RequestType.GET_GAME_STATE));
    }
}
