package cz.markovda.request.action;

import cz.markovda.game.LobbyGame;
import cz.markovda.view.Renderer;
import cz.markovda.view.Window;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#NEW_GAME} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class NewGameAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(String[] parameters) {
        Objects.requireNonNull(parameters);
        if (Renderer.getDisplayedWindow() == Window.LOBBY) {
            logger.debug("New game created!");
            Platform.runLater(() -> Renderer.addLobbyGame(new LobbyGame(parameters[0])));
        }
    }
}
