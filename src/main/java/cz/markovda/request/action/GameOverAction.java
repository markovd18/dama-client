package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.request.Request;
import cz.markovda.request.RequestType;
import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#GAME_OVER} and
 * {@link cz.markovda.request.Response#OPPONENT_LEFT} server response.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class GameOverAction implements ICommand {

    @Override
    public void execute(String[] parameters) {
        if (Renderer.getDisplayedWindow() == Window.GAME_SCREEN) {
            Platform.runLater(() -> {
                Renderer.showInformationWindow("Game over! Returning to lobby...");
                Connector.getInstance().sendRequest(new Request(RequestType.GET_GAMES));
            });
        }
    }
}
