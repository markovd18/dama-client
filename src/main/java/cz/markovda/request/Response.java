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
    CANNOT_LOGOUT(404);

    final int code;

    Response(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
