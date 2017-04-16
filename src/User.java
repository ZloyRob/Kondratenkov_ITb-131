import org.apache.commons.lang3.RandomStringUtils;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class
User {
    String login;
    String pass;
    int userId;
    String salt;

    public User(String login, String pass, int userId) {
        this.login = login;
        this.salt = addSalt();
        this.pass = addHash(pass);
        this.userId = userId;

    }

    public User(String login, String pass, int userId, String salt) {
        this.login = login;
        this.salt = salt;
        this.pass = pass;
        this.userId = userId;

    }

    public User() {

    }

    private String addSalt() {
        return RandomStringUtils.randomAscii(7);
    }

    private String addHash(String pass) {
        return md5Hex(md5Hex(pass) + this.salt);
    }
}
