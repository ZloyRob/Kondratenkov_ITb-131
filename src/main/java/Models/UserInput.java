package Models;

import java.util.Date;

public class UserInput {
    public String login;
    public String pass;
    public String path;
    public String role;
    public String vols;
    public int userId;
    public int resId;
    public String dss;
    public String des;
    public int vol;
    public Date ds;
    public Date de;

    public boolean isEmpty() {
        return (this.login == null || this.pass == null);
    }
}
