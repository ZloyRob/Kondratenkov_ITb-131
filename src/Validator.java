import org.apache.commons.cli.*;

import java.util.ArrayList;

public class Validator {
    AAAService aserv = new AAAService();
    CommandLineParser parser = new DefaultParser();
    Options opt = new Options();

    public UserInput allocation(UserInput us, String[] args) {
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

    public boolean authentication(ArrayList<User> Users, UserInput us) {
        boolean log = aserv.findUser(Users, us);
        if (log == false) System.exit(1);
        boolean pas = aserv.checkPas(Users, us);
        if (pas == false) System.exit(2);
        if (us.rl == null & us.path == null) pas = false;
        return pas;
    }

    public boolean authorization(boolean pr, UserInput us, ArrayList<Resource> Res) {
        if (pr == true) {
            boolean pro = aserv.checkRole(us);
            if (pro == false) System.exit(3);
            boolean az = aserv.checkAccess(Res, us);
            if (az == false) System.exit(4);
            return az;
        } else return pr;
    }

    public boolean accouting(boolean pr, UserInput us, ArrayList<Accounting> jur) {
        if (pr == true) {
            if (us.dss == null) {
                pr = false;
                return pr;
            } else {
                boolean vp = aserv.checkValDandV(us);
                if (vp == false) System.exit(5);
                aserv.addinJ(us, jur);
                return vp;
            }
        } else return pr;
    }
}
