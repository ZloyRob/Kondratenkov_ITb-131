import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {
    public boolean findUser(ArrayList<User> Users, UserInput us) {
        for (User user : Users) {
            if (us.login.equals(user.login)) {
                us.userId = user.userId;
                return true;
            }
        }
        return false;
    }

    public boolean checkPas(ArrayList<User> Users, UserInput us) {
        for (User user : Users) {
            if (us.userId == user.userId) {
                String tpas = md5Hex(md5Hex(us.pass) + user.salt);
                if (tpas.equals(user.pass)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkRole(UserInput us) {
        Enums.Roles[] allrole = Enums.Roles.values();
        for (Enums.Roles allroles : allrole) {
            if (us.rl.equals(allroles.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAccess(ArrayList<Resource> Res, UserInput us) {
        Integer a = null;
        boolean resourceAccess = false;
        boolean roleAccess = false;
        for (Resource resource : Res) {
            if (us.path.equals(resource.path)) { //находим нужный ресурс
                {
                    int ind = 0;
                    int indr = 0; //переменные для соотношения списков ролей и юзеров
                    for (Integer userId : resource.usersId) {
                        if (us.userId == userId) {
                            resourceAccess = true; //ищем в нем пользователя
                            indr = ind;
                        }
                        ind++;
                    }
                    if (us.rl.equals(resource.role.get(indr))) {//проверяем роль
                        roleAccess = true;
                    }
                }
                if (resourceAccess == true & roleAccess == true) return true;
            }
        }
        return false;
    }

    public boolean checkValDandV(UserInput us) {
        boolean dateVal = false;
        boolean volVal = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") {{
            setLenient(false);
        }};
        try {
            us.ds = sdf.parse(us.dss);
            us.de = sdf.parse(us.des);
            dateVal = true;

        } catch (Exception e) {
        }
        try {
            us.vol = Integer.valueOf(us.vols);
            if (us.vol > 0) {
                volVal = true;
            }
        } catch (NumberFormatException e) {
        }
        if (dateVal == true & volVal == true) return true;
        return false;
    }

    public void addinJ(UserInput us, ArrayList<Accounting> jur) {
        Accounting record = new Accounting(us.ds, us.de, us.vol, us.path, us.userId);
        jur.add(record);
    }

    public String addSal() {
        RandomStringUtils rsu = new RandomStringUtils();
        String result = rsu.randomAscii(7);
        return result;
    }

    public void addHash(User us) {
        us.pass = md5Hex(md5Hex(us.pass) + us.salt);
    }

    public String toString(ArrayList<Accounting> journal, int i) {
        String formatString = "Пользователь: %d Ресурс: %s Объем: %d Дата начала: %s Дата окончания: %s";
        return String.format(formatString, journal.get(i).userId, journal.get(i).res, journal.get(i).vol, journal.get(i).ds, journal.get(i).de);
    }
}
