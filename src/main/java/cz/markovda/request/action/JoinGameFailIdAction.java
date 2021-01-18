package cz.markovda.request.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#JOIN_GAME_FAIL_ID} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class JoinGameFailIdAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        // this should never happen
        logger.error("Invalid ID sent to server during join game request!");
    }
}
