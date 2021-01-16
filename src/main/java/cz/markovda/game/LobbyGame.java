package cz.markovda.game;

/**
 * Class representing created game shown in a lobby.
 *
 * @author David Markov
 * @since 16. 1. 2021
 */
public class LobbyGame {

    private final String opponentNick;

    public LobbyGame(String opponentNick) {
        this.opponentNick = opponentNick;
    }

    public String getOpponentNick() {
        return opponentNick;
    }
}
