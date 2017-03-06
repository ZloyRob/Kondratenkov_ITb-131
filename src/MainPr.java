import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * Created by Вадим on 06.03.2017.
 */
public class MainPr {
    public static void main(String[] args) {
        System.out.println("Hello World");
        // System.exit(2);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        User Alesha = new User("log1","pas1");
        User Misha = new User("log2","pas2");
        ArrayList<User> Users = new ArrayList();
        Users.add(Alesha);
        Users.add(Misha);
        for (int i = 0; i < 2; i++) {
            System.out.println(Users.get(i).login);
        }
        if (args[0].compareTo("login")==0) {
            if (args[1].compareTo("pass") ==0 ) {
                exit(0);
            } else exit(2);
        } else  System.exit(1);
    }
}
