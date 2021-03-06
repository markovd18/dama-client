package cz.markovda.request;

/**
 * Utility class for creating server requests.
 *
 * @author David Markov
 * @since 11. 1. 2021
 */
public class RequestFactory {

    public static Request createLoginRequest(final String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            return null;
        }

        return new Request(RequestType.CONNECT, nickname);
    }
}
