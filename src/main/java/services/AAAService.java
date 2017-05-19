package services;

import models.Accounting;
import models.User;
import models.UserInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {
    enum Roles {
        READ, WRITE, EXECUTE
    }

    private static final Logger log = LogManager.getLogger(AAAService.class.getName());

    public boolean isCheckPass(User usbd, UserInput us) {

        if (us.userId == usbd.userId) {
            if (md5Hex(md5Hex(us.getPass()) + usbd.salt).equals(usbd.pass)) {
                log.info("Пароль верен");
                return true;
            }
        }

        log.error("Пароль {} не подходит к пользователю {}", us.getPass(), us.getLogin());
        return false;
    }

    public boolean isCheckRole(UserInput us) {
        Roles[] allrole = Roles.values();
        for (Roles allroles : allrole) {
            if (us.getRole().equals(allroles.toString())) {
                log.info("Роль соответствует коллекции");
                return true;
            }
        }
        log.error("Роль - {} не соответствует коллекции", us.getRole());
        return false;
    }

    public boolean isCheckDate(UserInput us) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") {{
            setLenient(false);
        }};
        try {
            us.ds = sdf.parse(us.getDss());
            us.de = sdf.parse(us.getDes());
            log.info("Дата валидна");
            return true;

        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    public boolean isCheckVol(UserInput us) {

        try {
            us.vol = Integer.valueOf(us.getVols());

        } catch (NumberFormatException e) {
            log.error(e);
            return false;
        }
        if (us.vol > 0) {
            log.info("Объем валиден");
            return true;
        }
        log.error("Объем отрицателен {}", us.getVols());
        return false;
    }

    void addInJournal(Accounting journal, UserInput us) {
        journal.ds = us.ds;
        journal.de = us.de;
        journal.vol = us.vol;
        journal.resId = us.resId;
    }


}
