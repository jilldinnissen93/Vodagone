package nl.han.ica.oose.dea.domain;

public class User {
    private final String user;
    private final int user_id;
    private String token;

    public User(String user, int user_id, String token) {
        this.user = user;
        this.user_id = user_id;
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public int getUserId() {
        return user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
