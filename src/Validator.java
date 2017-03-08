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
        opt.addOption("l", "pass", true, "Password" );
        opt.addOption("l", "res", true, "Resource" );
        opt.addOption("l", "role", true, "Role" );
        opt.addOption("l", "ds", true, "Date start" );
        opt.addOption("l", "de", true, "Date finish" );
        opt.addOption("l", "vol", true, "Volume" );
        try{
        CommandLine line = parser.parse( opt, args );
            us.login=line.getOptionValue("login");
            us.pass=line.getOptionValue("pass");
            us.path=line.getOptionValue("res");
            us.rl=line.getOptionValue("role");
        }
        catch ( ParseException exp){
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }
        return  us;
    }
    public boolean Authentication(ArrayList<User> Users, UserInput us){
        int ind= aserv.FindUser(Users,us);
        if (ind == -1) System.exit(1);
        boolean tr = aserv.Check(Users,ind,us);
        if (tr==false) System.exit(2);
        return tr;
    }
    public boolean Authorization(boolean pr, UserInput us){

        return true;
    }
    public boolean Accouting(UserInput us){
        return true;
    }
}
