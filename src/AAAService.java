import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {
    enum Roles {
        READ, WRITE, EXECUTE
    }

    private static final Logger log = LogManager.getLogger(AAAService.class.getName());

    boolean isSearchUser(User usbd, UserInput us) {

        if (us.login.equals(usbd.login)) {
            us.userId = usbd.userId;
            log.info("Пользователь найден");
            return true;
        }

        log.error(String.format("Пользователь %s не найден", us.login));
        return false;
    }

    boolean isCheckPass(User usbd, UserInput us) {

        if (us.userId == usbd.userId) {
            if (md5Hex(md5Hex(us.pass) + usbd.salt).equals(usbd.pass)) {
                log.info("Пароль верен");
                return true;
            }
        }

        log.error(String.format("Пароль %s не подходит к пользователю %s", us.pass, us.login));
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
        log.error("Роль не соответствует коллекции");
        return false;
    }


    boolean isCheckAccess(Resource res, UserInput us) {
        if (us.path.equals(res.path)) { //проверяем не вернулся ли пустой ресурс
            us.resId = res.id;
            log.info(String.format("Доступ к ресурсу %s разрешен", us.path));
            return true;
        }
        log.error(String.format("Доступ к ресурсу %s запрещен", us.path));
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
            log.error(String.format("Дата не валидна(%s)", e.getMessage()));
        }

        return false;
    }

    boolean isCheckVol(UserInput us) {

        try {
            us.vol = Integer.valueOf(us.vols);

        } catch (NumberFormatException e) {
            log.error(String.format("Объем не валиден(%s)", e.getMessage()));
            return false;
        }
        if (us.vol > 0) {
            log.info("Объем валиден");
            return true;
        }
        log.error(String.format("Объем отрицателен(%s)", us.vols));
        return false;
    }

    void addInJournal(Accounting journal, UserInput us) {
        journal.ds = us.ds;
        journal.de = us.de;
        journal.vol = us.vol;
        journal.resId = us.resId;
    }


}
