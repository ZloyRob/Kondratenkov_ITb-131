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
        int ind = -1;
        for (int i = 0; i < Users.size(); i++) {
           if( args[0].compareTo(Users.get(i).login)==0){
               ind=i;
           }
        }
        if (ind!=-1) {
            if (args[1].compareTo(Users.get(ind).pass) ==0 ) {
                exit(0);
            } else exit(2);
        } else  System.exit(1);
    }
}
