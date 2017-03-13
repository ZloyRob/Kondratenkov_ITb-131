import org.apache.commons.cli.*;

import java.util.ArrayList;

public class Validator {
    private AAAService aaaService = new AAAService();
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
            us.role = line.getOptionValue("role");
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

    boolean isAuthentication(ArrayList<User> Users, UserInput us) {
        if (!aaaService.isSearchUser(Users, us)) {
            System.exit(1);
        }
        if (!aaaService.isCheckPass(Users, us)) {
            System.exit(2);
        }
        return true;
    }

    boolean isAuthorization(ArrayList<Resource> Res, UserInput us, boolean isAuthentication) {
        if (isAuthentication & us.role != null & us.path != null) {
            if (!aaaService.isCheckRole(us)) {
                System.exit(3);
            }
            if (!aaaService.isCheckAccess(Res, us)) {
                System.exit(4);
            }
            return true;
        } else {
            return false;
        }
    }

    boolean isAccouting(ArrayList<Accounting> journal, UserInput us, boolean isAuthorization) {
        if (isAuthorization & us.dss != null) {
            if (!aaaService.isCheckDate(us) & !aaaService.isCheckVol(us)) {
                System.exit(5);
            }
            aaaService.addInJournal(journal, us);
            return true;
        } else {
            return false;
        }
    }
}
