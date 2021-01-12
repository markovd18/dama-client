package cz.markovda.connection.vo;

/**
 * VO representing user.
 *
 * @author David Markov
 * @since 12. 1. 2020
 */
public class User {

    private int userId;
    private String nickname;

    public User() {

    }

    public User(String nickname) {
        this(0, nickname);
    }

    public User(int userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
