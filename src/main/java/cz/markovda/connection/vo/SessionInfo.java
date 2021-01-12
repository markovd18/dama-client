package cz.markovda.connection.vo;

/**
 * SessionInfo carries information about current server-client connection session. It has information about
 * what server are we connected to, what ID did server assign to the user and what is user's chosen nickname.
 *
 * @author David Markov
 * @since 12. 1. 2021
 */
public class SessionInfo {

    private Server server;
    private User user;

    public SessionInfo(Server server, User user) {
        this.server = server;
        this.user = user;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
