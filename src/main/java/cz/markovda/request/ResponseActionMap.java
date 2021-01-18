package cz.markovda.request;

import cz.markovda.request.action.CannotLogoutAction;
import cz.markovda.request.action.CannotReconnectAction;
import cz.markovda.request.action.ConnectInvalidIdAction;
import cz.markovda.request.action.ConnectInvalidNickAction;
import cz.markovda.request.action.CreateGameFailIdAction;
import cz.markovda.request.action.CreateGameFailStateAction;
import cz.markovda.request.action.CreateGameOkAction;
import cz.markovda.request.action.ExitGameFailIdAction;
import cz.markovda.request.action.ExitGameFailState;
import cz.markovda.request.action.ExitGameOkAction;
import cz.markovda.request.action.GameDeletedAction;
import cz.markovda.request.action.GetGameStateOkAction;
import cz.markovda.request.action.GetGamesFailAction;
import cz.markovda.request.action.GetGamesOkAction;
import cz.markovda.request.action.ICommand;
import cz.markovda.request.action.JoinGameFailIdAction;
import cz.markovda.request.action.JoinGameFailNickAction;
import cz.markovda.request.action.JoinGameFailStateAction;
import cz.markovda.request.action.JoinGameOkAction;
import cz.markovda.request.action.LogoutInvalidUserAction;
import cz.markovda.request.action.LogoutOkAction;
import cz.markovda.request.action.NewConnectionOkAction;
import cz.markovda.request.action.NewGameAction;
import cz.markovda.request.action.ReconnectOkAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to map individual response codes of the server to appropriate reactions.
 * @author David Markov
 * @since 18. 1. 2021
 * @see Response
 */
public class ResponseActionMap {

    private final Map<Integer, ICommand> responseReactions;

    /**
     * Constructs new response reaction map.
     */
    public ResponseActionMap() {

        responseReactions = new HashMap<>();
        responseReactions.put(Response.NEW_CONNECTION_OK.getCode(), new NewConnectionOkAction());
        responseReactions.put(Response.RECONNECT_OK.getCode(), new ReconnectOkAction());
        responseReactions.put(Response.CONNECT_INVALID_NICK.getCode(), new ConnectInvalidNickAction());
        responseReactions.put(Response.CONNECT_INVALID_ID.getCode(), new ConnectInvalidIdAction());
        responseReactions.put(Response.CANNOT_RECONNECT.getCode(), new CannotReconnectAction());
        responseReactions.put(Response.LOGOUT_OK.getCode(), new LogoutOkAction());
        responseReactions.put(Response.LOGOUT_INVALID_USER.getCode(), new LogoutInvalidUserAction());
        responseReactions.put(Response.CANNOT_LOGOUT.getCode(), new CannotLogoutAction());
        responseReactions.put(Response.GET_GAMES_OK.getCode(), new GetGamesOkAction());
        responseReactions.put(Response.GET_GAMES_FAIL.getCode(), new GetGamesFailAction());
        responseReactions.put(Response.CREATE_GAME_OK.getCode(), new CreateGameOkAction());
        responseReactions.put(Response.CREATE_GAME_FAIL_STATE.getCode(), new CreateGameFailStateAction());
        responseReactions.put(Response.CREATE_GAME_FAIL_ID.getCode(), new CreateGameFailIdAction());
        responseReactions.put(Response.EXIT_GAME_OK.getCode(), new ExitGameOkAction());
        responseReactions.put(Response.EXIT_GAME_FAIL_ID.getCode(), new ExitGameFailIdAction());
        responseReactions.put(Response.EXIT_GAME_FAIL_STATE.getCode(), new ExitGameFailState());
        responseReactions.put(Response.NEW_GAME.getCode(), new NewGameAction());
        responseReactions.put(Response.GAME_DELETED.getCode(), new GameDeletedAction());
        responseReactions.put(Response.JOIN_GAME_OK.getCode(), new JoinGameOkAction());
        responseReactions.put(Response.JOIN_GAME_FAIL_ID.getCode(), new JoinGameFailIdAction());
        responseReactions.put(Response.JOIN_GAME_FAIL_STATE.getCode(), new JoinGameFailStateAction());
        responseReactions.put(Response.JOIN_GAME_FAIL_NICK.getCode(), new JoinGameFailNickAction());
        responseReactions.put(Response.GET_GAME_STATE_OK.getCode(), new GetGameStateOkAction());

    }

    /**
     * Returns executable reaction for given response code.
     *
     * @param code server response code
     * @return executable reaction for the response
     */
    public ICommand get(final Integer code) {
        return responseReactions.get(code);
    }
}
