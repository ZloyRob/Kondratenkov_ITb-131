import java.util.Date;

/**
 * Created by Вадим on 09.03.2017.
 */
public class Accounting {
    Date ds;
    Date de;
    int vol;
    String res;
    int userId;
    public Accounting(Date ds, Date de, int vol, String res,int userId){
        this.ds=ds;
        this.de=de;
        this.vol=vol;
        this.res=res;
        this.userId=userId;
    }
}
