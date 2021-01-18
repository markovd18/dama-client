package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import cz.markovda.view.Window;

import java.util.Objects;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#NEW_CONNECTION_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class NewConnectionOkAction implements ICommand {


    @Override
    public void execute(String[] parameters) {
        Objects.requireNonNull(parameters);
        if (Renderer.getDisplayedWindow() == Window.LOGIN_SCREEN) {
            Connector.getInstance().getSessionInfo().getUser().setUserId(Integer.parseInt(parameters[0]));
            Connector.getInstance().sendRequest(new Request(RequestType.GET_GAMES));
        }
    }
}
