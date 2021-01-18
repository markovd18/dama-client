package cz.markovda.request.action;

import cz.markovda.connection.Connector;
import cz.markovda.connection.vo.SessionInfo;
import cz.markovda.game.LobbyGame;
import cz.markovda.view.Renderer;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing the reaction on {@link cz.markovda.request.Response#GET_GAMES_OK} server response.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class GetGamesOkAction implements ICommand {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(final String[] parameters) {
        Objects.requireNonNull(parameters, "Array of nicknames may not be null!");
        switch (Renderer.getDisplayedWindow()) {
            case LOGIN_SCREEN:
            case LOADING_SCREEN:
            case GAME_SCREEN:
                displayLobby(parameters);
                break;
        }

    }

    private void displayLobby(final String[] parameters) {
            logger.debug("Aquiring lobby games and displaying them...");
            Platform.runLater(() -> {
                List<LobbyGame> games = new ArrayList<>();
                for (String nickname : parameters) {
                    games.add(new LobbyGame(nickname));
                }

                SessionInfo sessionInfo = Connector.getInstance().getSessionInfo();
                String serverInfo = sessionInfo.getServer().getAddress() + ':' +
                        sessionInfo.getServer().getPort();
                Renderer.displayLobby(serverInfo, sessionInfo.getUser().getNickname(), games);
            });
    }
}
