package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#LOGOUT_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class LogoutOkAction implements ICommand {

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.LOBBY) {
            Connector.getInstance().getSessionInfo().getUser().setUserId(0);
            Connector.getInstance().getSessionInfo().getUser().setNickname(null);
            Platform.runLater(() -> Renderer.displayLoginScreen(
                    Connector.getInstance().getSessionInfo().getServer().getAddress(),
                    String.valueOf(Connector.getInstance().getSessionInfo().getServer().getPort())));
        }
    }
}
