package cz.markovda.request;

import cz.markovda.request.action.CannotReconnectAction;
import cz.markovda.request.action.ConnectInvalidNickAction;
import cz.markovda.request.action.ConnectionDroppedAction;
import cz.markovda.request.action.CreateGameOkAction;
import cz.markovda.request.action.ExitGameOkAction;
import cz.markovda.request.action.GameDeletedAction;
import cz.markovda.request.action.GameOverAction;
import cz.markovda.request.action.GameResumedAction;
import cz.markovda.request.action.GameStartedAction;
import cz.markovda.request.action.GeneralErrorAction;
import cz.markovda.request.action.GetGameStateOkAction;
import cz.markovda.request.action.GetGamesOkAction;
import cz.markovda.request.action.ICommand;
import cz.markovda.request.action.InvalidIdAction;
import cz.markovda.request.action.InvalidStateAction;
import cz.markovda.request.action.InvalidTurnAction;
import cz.markovda.request.action.JoinGameFailNickAction;
import cz.markovda.request.action.JoinGameOkAction;
import cz.markovda.request.action.LogoutOkAction;
import cz.markovda.request.action.NewConnectionOkAction;
import cz.markovda.request.action.NewGameAction;
import cz.markovda.request.action.OpponentDcAction;
import cz.markovda.request.action.PingAction;
import cz.markovda.request.action.ReconnectOkAction;
import cz.markovda.request.action.TurnOkAction;

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
        responseReactions.put(Response.LOGOUT_OK.getCode(), new LogoutOkAction());
        responseReactions.put(Response.GET_GAMES_OK.getCode(), new GetGamesOkAction());
        responseReactions.put(Response.CREATE_GAME_OK.getCode(), new CreateGameOkAction());
        responseReactions.put(Response.EXIT_GAME_OK.getCode(), new ExitGameOkAction());
        responseReactions.put(Response.JOIN_GAME_OK.getCode(), new JoinGameOkAction());
        responseReactions.put(Response.GET_GAME_STATE_OK.getCode(), new GetGameStateOkAction());
        responseReactions.put(Response.TURN_OK.getCode(), new TurnOkAction());

        responseReactions.put(Response.CONNECT_INVALID_NICK.getCode(), new ConnectInvalidNickAction());
        responseReactions.put(Response.CANNOT_RECONNECT.getCode(), new CannotReconnectAction());
        responseReactions.put(Response.JOIN_GAME_FAIL_NICK.getCode(), new JoinGameFailNickAction());
        responseReactions.put(Response.INVALID_TURN.getCode(), new InvalidTurnAction());

        responseReactions.put(Response.GAME_OVER.getCode(), new GameOverAction());
        responseReactions.put(Response.NEW_GAME.getCode(), new NewGameAction());
        responseReactions.put(Response.GAME_DELETED.getCode(), new GameDeletedAction());
        responseReactions.put(Response.GAME_STARTED.getCode(), new GameStartedAction());
        responseReactions.put(Response.OPPONENT_LEFT.getCode(), new GameOverAction());
        responseReactions.put(Response.OPPONENT_DC.getCode(), new OpponentDcAction());
        responseReactions.put(Response.GAME_RESUMED.getCode(), new GameResumedAction());

        responseReactions.put(Response.INVALID_ID.getCode(), new InvalidIdAction());
        responseReactions.put(Response.INVALID_STATE.getCode(), new InvalidStateAction());
        responseReactions.put(Response.GENERAL_ERROR.getCode(), new GeneralErrorAction());
        responseReactions.put(Response.CONNECTION_DROPPED.getCode(), new ConnectionDroppedAction());

        responseReactions.put(Response.PING.getCode(), new PingAction());
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
