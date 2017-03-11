import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AAAService {
    boolean findUser(ArrayList<User> Users, UserInput us) {
        for (User user : Users) {
            if (us.login.equals(user.login)) {
                us.userId = user.userId;
                return true;
            }
        }
        return false;
    }

    boolean checkPas(ArrayList<User> Users, UserInput us) {
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

    boolean checkRole(UserInput us) {
        Enums.Roles[] allrole = Enums.Roles.values();
        for (Enums.Roles allroles : allrole) {
            if (us.rl.equals(allroles.toString())) {
                return true;
            }
        }
        return false;
    }

    boolean checkAccess(ArrayList<Resource> Res, UserInput us) {
       // us.path+=".";
        String[] masOfPath=us.path.split("\\.");
        String findPath="";
        for(String string:masOfPath) {
            findPath+=string;
            for (Resource resource : Res) {
                if (findPath.equals(resource.path)) { //находим нужный ресурс
                    if (us.userId == resource.usersId) {
                        if (us.rl.equals(resource.role)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean checkValDandV(UserInput us) {
        boolean dateVal = false;
        boolean volVal = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") {{
            setLenient(false);
        }};
        try {
            us.ds = sdf.parse(us.dss);
            us.de = sdf.parse(us.des);
            dateVal = true;

        } catch (Exception ignored) {
        }
        try {
            us.vol = Integer.valueOf(us.vols);
            if (us.vol > 0) {
                volVal = true;
            }
        } catch (NumberFormatException ignored) {
        }
        return dateVal & volVal;
    }

    void addinJ(UserInput us, ArrayList<Accounting> jur) {
        Accounting record = new Accounting(us.ds, us.de, us.vol, us.path, us.userId);
        jur.add(record);
    }

    String addSal() {
        return  RandomStringUtils.randomAscii(7);

    }

    void addHash(User us) {
        us.pass = md5Hex(md5Hex(us.pass) + us.salt);
    }


}
