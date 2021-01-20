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
    LOGOUT_OK(202),
    GET_GAMES_OK(203),
    CREATE_GAME_OK(204),
    EXIT_GAME_OK(205),
    JOIN_GAME_OK(206),
    GET_GAME_STATE_OK(207),
    TURN_OK(208),

    CONNECT_INVALID_NICK(400),
    CANNOT_RECONNECT(401),
    JOIN_GAME_FAIL_NICK(402),
    INVALID_TURN(403),

    GAME_OVER(210),
    NEW_GAME(250),
    GAME_DELETED(260),
    GAME_STARTED(270),
    OPPONENT_LEFT(280),
    OPPONENT_DC(290),
    GAME_RESUMED(295),

    INVALID_ID(410),
    INVALID_STATE(420),
    GENERAL_ERROR(450),
    CONNECTION_DROPPED(460);

    final int code;

    Response(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
