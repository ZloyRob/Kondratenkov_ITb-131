import org.apache.commons.cli.*;

import java.util.ArrayList;

/**
 * Created by Вадим on 08.03.2017.
 */
public class Validator {
    AAAService aserv = new AAAService();
    CommandLineParser parser = new DefaultParser();

    Options opt = new Options();
    public UserInput Allocation(UserInput us, String[] args){
        opt.addOption("l", "login", true, "Login" );
        opt.addOption("p", "pass", true, "Password" );
        opt.addOption("re", "res", false, "Resource" );
        opt.addOption("ro", "role", false, "Role" );
        opt.addOption("ds", "ds", false, "Date start" );
        opt.addOption("de", "de", false, "Date finish" );
        opt.addOption("v", "vol", false, "Volume" );
        opt.addOption("h", "help", false, "Help" );
        try{
        CommandLine line = parser.parse( opt, args );
            us.login=line.getOptionValue("login");
            us.pass=line.getOptionValue("pass");
            us.path=line.getOptionValue("res");
            us.rl=line.getOptionValue("role");
            us.dss=line.getOptionValue("ds");
            us.des=line.getOptionValue("de");
            us.vols=line.getOptionValue("vol");
            if(line.hasOption("h")|| null==us.login || null==us.pass ){
                HelpFormatter fo = new HelpFormatter();
                fo.printHelp("Help", opt);
                System.exit(0);
            }
        }
        catch ( ParseException exp){
            HelpFormatter fo = new HelpFormatter();
            fo.printHelp("Help", opt);
            System.exit(0);
        }
        return  us;
    }
    public boolean Authentication(ArrayList<User> Users, UserInput us){
        int ind= aserv.FindUser(Users,us);
        if (ind == -1) System.exit(1);
        boolean tr = aserv.CheckPas(Users,ind,us);
        if (tr==false) System.exit(2);
        return tr;
    }
    public boolean Authorization(boolean pr, UserInput us, ArrayList<Resource> Res){
        if(pr==true){
        boolean pro= aserv.CheckRole(us);
        if (pro==false) System.exit(3);
        boolean az=aserv.CheckAccess(Res,us);
        if (az==false) System.exit(4);
        return az;
        }
        else return pr;
    }
    public boolean Accouting(boolean pr, UserInput us,ArrayList<Accounting> jur){
        if(pr==true){
            boolean vp=aserv.CheckValDandV(us);
            if (vp==false) System.exit(5);
            aserv.AddinJ(us,jur);
            return vp;
        }else
        return pr;
    }
}
