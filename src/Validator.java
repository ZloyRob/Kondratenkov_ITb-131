import org.apache.commons.cli.*;

import java.util.ArrayList;

public class Validator {
    private AAAService aserv = new AAAService();
    private CommandLineParser parser = new DefaultParser();
    private Options opt = new Options();

    UserInput allocation(UserInput us, String[] args) {
        opt.addOption("l", "login", true, "Login");
        opt.addOption("p", "pass", true, "Password");
        opt.addOption("re", "res", true, "Resource");
        opt.addOption("ro", "role", true, "Role");
        opt.addOption("ds", "ds", true, "Date start");
        opt.addOption("de", "de", true, "Date finish");
        opt.addOption("v", "vol", true, "Volume");
        opt.addOption("h", "help", true, "Help");
        try {
            CommandLine line = parser.parse(opt, args);
            us.login = line.getOptionValue("login");
            us.pass = line.getOptionValue("pass");
            us.path = line.getOptionValue("res");
            us.rl = line.getOptionValue("role");
            us.dss = line.getOptionValue("ds");
            us.des = line.getOptionValue("de");
            us.vols = line.getOptionValue("vol");
            if (line.hasOption("h") || null == us.login || null == us.pass) {
                HelpFormatter fo = new HelpFormatter();
                fo.printHelp("Help", opt);
                System.exit(0);
            }
        } catch (ParseException exp) {
            HelpFormatter fo = new HelpFormatter();
            fo.printHelp("Help", opt);
            System.exit(0);
        }
        return us;
    }

    boolean authentication(ArrayList<User> Users, UserInput us) {
        boolean log = aserv.findUser(Users, us);
        if (!log) System.exit(1);
        boolean pas = aserv.checkPas(Users, us);
        if (!pas) System.exit(2);
        //if (us.rl == null & us.path == null) pas = false;
        return true;
    }

    boolean authorization(boolean pr, UserInput us, ArrayList<Resource> Res) {
        if (pr & us.rl!=null & us.path!=null) {
            boolean pro = aserv.checkRole(us);
            if (!pro) System.exit(3);
            boolean az = aserv.checkAccess(Res, us);
            if (!az) System.exit(4);
            return true;
        } else return false;
    }

    boolean accouting(boolean pr, UserInput us, ArrayList<Accounting> jur) {
        if (pr & us.dss!=null) {
                boolean vp = aserv.checkValDandV(us);
                if (!vp) System.exit(5);
                aserv.addinJ(us, jur);
                return true;
            }
         else return false;
    }
}
