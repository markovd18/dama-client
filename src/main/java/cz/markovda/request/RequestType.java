package cz.markovda.request;

/**
 * All types of requests we can send.
 */
public enum RequestType {
    /**
     * Request to connect to the server. Requires player nickname as a parameter.
     */
    CONNECT,
    /**
     * Request to logout the player from the server. Doesn't disconnect.
     */
    LOGOUT,
    /**
     * Request to return all games waiting for second player to join.
     */
    GET_GAMES,
    /**
     * Request to create new game and join it.
     */
    CREATE_GAME,
    /**
     * Request to exit current game.
     */
    EXIT_GAME
}
