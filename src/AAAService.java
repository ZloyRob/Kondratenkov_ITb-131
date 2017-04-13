

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {
    enum Roles {
        READ, WRITE, EXECUTE
    }
    private static final Logger log = LogManager.getLogger(AAAService.class.getName());
    boolean isSearchUser(ArrayList<User> Users, UserInput us) {
        for (User user : Users) {
            if (us.login.equals(user.login)) {
                us.userId = user.userId;
                log.info("Пользователь найден");
                return true;
            }
        }
        log.error(String.format("Пользователь %s не найден", us.login));
        return false;
    }

    boolean isCheckPass(ArrayList<User> Users, UserInput us) {
        for (User user : Users) {
            if (us.userId == user.userId) {
                if (md5Hex(md5Hex(us.pass) + user.salt).equals(user.pass)) {
                    log.info("Пароль верен");
                    return true;
                }
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

    /**
     * Метод проверяет разрешен ли пользователю доступ к ресурсу
     * Поиск происходит начиная с головы пути ресурса и с каждой итерацией спускается все ниже
     *
     * @return Возвращается true если доступ разрешен
     */
    boolean isCheckAccess(ArrayList<Resource> Res, UserInput us) {
        String[] masOfPath = us.path.split("\\."); //разбиваем путь по уровням
        String findPath = "";
        for (String string : masOfPath) {
            findPath += string; //опускаемся на уровень ниже
            for (Resource resource : Res) {
                if (findPath.equals(resource.path)) { //находим нужный ресурс
                    if (us.userId == resource.usersId) { //проверям пользователя и роль
                        if (us.role.equals(resource.role)) {
                            us.resId=resource.id;
                            log.info(String.format("Доступ к ресурсу %s разрешен", us.path));
                            return true;
                        }
                    }
                }
            }
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

        } catch (Exception ignored) {
        }
        log.error("Дата не валидна");
        return false;
    }

    boolean isCheckVol(UserInput us) {

        try {
            us.vol = Integer.valueOf(us.vols);
            if (us.vol > 0) {
                log.info("Объем валиден");
                return true;
            }
        } catch (NumberFormatException ignored) {
        }
        log.error(String.format("Объем не валиден(%s)", us.vol));
        return false;
    }

    void addInJournal(ArrayList<Accounting> journal, UserInput us) {
        Accounting record = new Accounting(us.ds, us.de, us.vol, us.resId);
        journal.add(record);
    }


}
