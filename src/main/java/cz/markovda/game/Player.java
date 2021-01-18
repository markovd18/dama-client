package cz.markovda.game;

import java.util.List;

/**
 * Class representing a player in the game of Draughts.
 *
 * @author David Markov
 * @since 18. 1. 2021
 */
public class Player {

    private final String nickname;
    private final List<GameToken> tokens;

    public Player(final String nickname, final List<GameToken> tokens) {
        this.nickname = nickname;
        this.tokens = tokens;
    }

    public String getNickname() {
        return nickname;
    }

    public List<GameToken> getTokens() {
        return tokens;
    }
}
