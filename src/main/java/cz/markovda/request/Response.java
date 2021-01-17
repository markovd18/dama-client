package cz.markovda.request;

/**
 * All possible responses to a request from server.
 *
 * @author David Markov
 * @since 14. 1. 2021
 */
public enum Response {
    NEW_CONNECTION_OK(200),
    RECONNECT_OK(201),
    CONNECT_INVALID_NICK(400),
    CONNECT_INVALID_ID(401),
    CANNOT_RECONNECT(402),
    LOGOUT_OK(202),
    LOGOUT_INVALID_USER(403),
    CANNOT_LOGOUT(404),
    GET_GAMES_OK(203),
    GET_GAMES_FAIL(405),
    CREATE_GAME_OK(204),
    CREATE_GAME_FAIL_STATE(406),
    CREATE_GAME_FAIL_ID(407),
    EXIT_GAME_OK(205),
    EXIT_GAME_FAIL_ID(408),
    EXIT_GAME_FAIL_STATE(409),
    NEW_GAME(250),
    GAME_DELETED(260),
    JOIN_GAME_OK(206),
    JOIN_GAME_FAIL_ID(410),
    JOIN_GAME_FAIL_STATE(411),
    JOIN_GAME_FAIL_NICK(412),
    GET_GAME_STATE_OK(207),
    GET_GAME_STATE_FAIL_ID(413),
    GET_GAME_STATE_FAIL_STATE(414);

    final int code;

    Response(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
