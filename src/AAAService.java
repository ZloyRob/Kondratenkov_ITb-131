import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {
    enum Roles {
        READ, WRITE, EXECUTE
    }

    boolean isSearchUser(ArrayList<User> Users, UserInput us) {
        for (User user : Users) {
            if (us.login.equals(user.login)) {
                us.userId = user.userId;
                return true;
            }
        }
        return false;
    }

    boolean isCheckPass(ArrayList<User> Users, UserInput us) {
        for (User user : Users) {
            if (us.userId == user.userId) {
                if (md5Hex(md5Hex(us.pass) + user.salt).equals(user.pass)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isCheckRole(UserInput us) {
        Roles[] allrole = Roles.values();
        for (Roles allroles : allrole) {
            if (us.role.equals(allroles.toString())) {
                return true;
            }
        }
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
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean isCheckDate(UserInput us) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") {{
            setLenient(false);
        }};
        try {
            us.ds = sdf.parse(us.dss);
            us.de = sdf.parse(us.des);
            return true;

        } catch (Exception ignored) {
        }
        return false;
    }

    boolean isCheckVol(UserInput us) {

        try {
            us.vol = Integer.valueOf(us.vols);
            if (us.vol > 0) {
                return true;
            }
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    void addInJournal(ArrayList<Accounting> journal, UserInput us) {
        Accounting record = new Accounting(us.ds, us.de, us.vol, us.path, us.userId);
        journal.add(record);
    }


}
