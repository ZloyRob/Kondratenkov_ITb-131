
public class
User {
    String login;
    String pass;
    int userId;
    String salt;

    public User(String login, String salt, String pass, int userId) {
        this.login = login;
        this.pass = pass;
        this.userId = userId;
        this.salt = salt;
    }
}
