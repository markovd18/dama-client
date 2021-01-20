package cz.markovda.request.action;

import cz.markovda.connection.Connector;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#PING} server response.
 *
 * @author David Markov
 * @since 20. 1. 2021
 */
public class PingAction implements ICommand{

    @Override
    public void execute(String[] parameters) {
        Connector.getInstance().setServerUp(true);
    }
}
