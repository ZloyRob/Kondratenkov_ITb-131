package services;

import models.Accounting;
import models.Resource;
import models.User;
import models.UserInput;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validator {
    private AAAService aaaService = new AAAService();
    private CommandLineParser parser = new DefaultParser();
    private Options opt = new Options();
    private static final Logger log = LogManager.getLogger(Validator.class.getName());

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
            us.role = line.getOptionValue("role");
            us.dss = line.getOptionValue("ds");
            us.des = line.getOptionValue("de");
            us.vols = line.getOptionValue("vol");
            if (line.hasOption("h") || null == us.login || null == us.pass) {
                HelpFormatter fo = new HelpFormatter();
                fo.printHelp("Help", opt);
                log.info("Exit 0");
                System.exit(0);

            }
        } catch (ParseException exp) {
            HelpFormatter fo = new HelpFormatter();
            fo.printHelp("Help", opt);
            log.error("Неизвестные параметры");
            log.info("Exit 0");
            System.exit(0);
        }
        return us;
    }

    public boolean whatH(String[] args) {
        opt.addOption("h", "help", false, "Help");
        try {
            CommandLine line = parser.parse(opt, args);
            if (line.hasOption("h")) {
                return true;
            }
        } catch (ParseException ignored) {
        }
        return false;
    }

    public boolean isAuthentication(User usbd, UserInput us) {
        if (usbd == null) {
            log.info("Exit 1");
            System.exit(1);
        } else {
            us.userId = usbd.userId;
        }
        if (!aaaService.isCheckPass(usbd, us)) {
            log.info("Exit 2");
            System.exit(2);
        }
        log.info("Аутентификация пройдена");
        return true;
    }

    public int isAuthenticationTest(User usbd, UserInput us) {
        if (usbd == null) {
            log.error("Пользователь - {} не найден", us.login);
            return 1;
        } else {
            us.userId = usbd.userId;
        }
        if (!aaaService.isCheckPass(usbd, us)) {
            log.error("Пароль - {} не подходит {}", us.pass, us.login);
            return 2;
        }
        return 0;
    }

    public boolean isAuthorization(Resource Res, UserInput us, boolean isAuthentication) {
        if (isAuthentication & us.role != null & us.path != null) {
            if (!aaaService.isCheckRole(us)) {
                log.info("Exit 3");
                System.exit(3);
            }
            if (Res == null) {
                log.info("Exit 4");
                System.exit(4);
            } else {
                us.resId = Res.id;
            }

            log.info("Авторизация пройдена");
            return true;
        } else {
            return false;
        }
    }

    public int isAuthorizationTest(Resource Res, UserInput us) {
        if (!aaaService.isCheckRole(us)) {
            return 3;
        }
        if (Res == null) {
            log.error("Доступ к ресурсу - {} запрещен", us.path);
            return 4;
        }
        log.info("Авторизация пройдена");
        return 0;
    }

    public boolean isAccouting(Accounting journal, UserInput us, boolean isAuthorization) {
        if (isAuthorization & us.dss != null) {
            if (!aaaService.isCheckDate(us) || !aaaService.isCheckVol(us)) {
                log.info("Exit 5");
                System.exit(5);
            }
            aaaService.addInJournal(journal, us);
            log.info("Аккаунтинг пройден");
            return true;
        } else {
            return false;
        }
    }

    public int isAccoutingTest(UserInput us) {
        if (!aaaService.isCheckDate(us) || !aaaService.isCheckVol(us)) {

            return 5;
        }
        log.info("Аккаунтинг пройден");
        return 0;
    }

}
