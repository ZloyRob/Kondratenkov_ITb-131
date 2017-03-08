import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Вадим on 08.03.2017.
 */
public class AAAService {
    public int FindUser(ArrayList<User> Users, UserInput us){
        int a=-1;
        for (int i = 0; i < Users.size(); i++) {
            if( us.login.compareTo(Users.get(i).login)==0){
                a=i;
                us.userId=Users.get(i).userId;
            }
        }
        return a;
    }
    public boolean CheckPas(ArrayList<User> Users, int ind,UserInput us ){
        boolean trf=false;
        if (ind!=-1) {
            if (us.pass.compareTo(Users.get(ind).pass) ==0 ) {
                trf=true;
            }
        }
        return trf;
    }
    public boolean CheckRole(UserInput us){
        Roles[] allrole = Roles.values();
        boolean tr=false;
        for (int i = 0; i <allrole.length ; i++) {
            if (us.rl.compareTo(allrole[i].toString()) == 0){
                tr=true;
            }
        }
        return tr;
    }
    public boolean CheckAccess(ArrayList<Resource> Res, UserInput us){
        int a=-1;
        boolean tre=false;
        boolean tro=false;
        boolean tf=false;
        for (int i = 0; i < Res.size(); i++) {
            if( us.path.compareTo(Res.get(i).path)==0){
                a=i;
            }
            if (a!=-1){
                for (int j = 0; j <Res.get(a).usersId.size() ; j++) {
                    if(us.userId==Res.get(a).usersId.get(j)){
                        tre=true;
                    }
                }
                for (int j = 0; j <Res.get(a).usersId.size() ; j++) {
                    if(us.rl.compareTo(Res.get(a).role.get(j))==0){
                        tro=true;
                    }
                }
            }
            if (tre==true & tro==true) tf=true;
        }
        return tf;
    }
    public boolean CheckValDandV(UserInput us){
        boolean dt=false;
        boolean in=false;
        boolean f = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") {{ setLenient(false); }};
        try{
            us.ds=sdf.parse(us.dss);
            us.de=sdf.parse(us.des);
            dt=true;

        }catch (Exception e) {}
        try{
            us.vol=Integer.valueOf(us.vols);
            if(us.vol>0){in=true;}
        }catch (NumberFormatException e){}
        if(dt==true & in == true) f=true;
        return  f;
    }
    public void AddinJ(UserInput us, ArrayList<Accounting> jur){
        Accounting record = new Accounting(us.ds, us.de, us.vol,us.path, us.userId);
        jur.add(record);
    }
}
