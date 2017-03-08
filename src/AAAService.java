import java.util.ArrayList;

/**
 * Created by Вадим on 08.03.2017.
 */
public class AAAService {
    public int FindUser(ArrayList<User> Users, UserInput us){
        int a=-1;
        for (int i = 0; i < Users.size(); i++) {
            if( us.login.compareTo(Users.get(i).login)==0){
                a=i ;
            }
        }
        return a;
    }
    public boolean Check(ArrayList<User> Users, int ind,UserInput us ){
        boolean trf=false;
        if (ind!=-1) {
            if (us.pass.compareTo(Users.get(ind).pass) ==0 ) {
                trf=true;
            }
        }
        return trf;
    }

}
