import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {
    enum Roles {
        READ, WRITE, EXECUTE
    }

    private static final Logger log = LogManager.getLogger(AAAService.class.getName());

    boolean isCheckPass(User usbd, UserInput us) {

        if (us.userId == usbd.userId) {
            if (md5Hex(md5Hex(us.pass) + usbd.salt).equals(usbd.pass)) {
                log.info("Пароль верен");
                return true;
            }
        }

        log.error("Пароль {} не подходит к пользователю {}", us.pass, us.login);
        return false;
    }

    boolean isCheckRole(UserInput us) {
        Roles[] allrole = Roles.values();
        for (Roles allroles : allrole) {
            if (us.role.equals(allroles.toString())) {
                log.info("Роль соответствует коллекции");
                return true;
            }
        }
        log.error("Роль - {} не соответствует коллекции", us.role);
        return false;
    }

    boolean isCheckDate(UserInput us) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") {{
            setLenient(false);
        }};
        try {
            us.ds = sdf.parse(us.dss);
            us.de = sdf.parse(us.des);
            log.info("Дата валидна");
            return true;

        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    boolean isCheckVol(UserInput us) {

        try {
            us.vol = Integer.valueOf(us.vols);

        } catch (NumberFormatException e) {
            log.error(e);
            return false;
        }
        if (us.vol > 0) {
            log.info("Объем валиден");
            return true;
        }
        log.error("Объем отрицателен {}", us.vols);
        return false;
    }

    void addInJournal(Accounting journal, UserInput us) {
        journal.ds = us.ds;
        journal.de = us.de;
        journal.vol = us.vol;
        journal.resId = us.resId;
    }


}
