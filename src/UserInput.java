import java.util.Date;

/**
 * Created by Вадим on 08.03.2017.
 */
public class UserInput {
    String login;
    String pass;
    String path;
    String rl;
    int vol;
    Date ds;
    Date de;
    public UserInput(String login, String pass, String path, String rl, int vol, Date ds, Date de){
        this.login=login;
        this.pass=pass;
        this.path=path;
        this.rl=rl;
        this.vol=vol;
        this.ds=ds;
        this.de=de;

    }
}
