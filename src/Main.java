import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.exit;

/**
 * Created by Вадим on 06.03.2017.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World");
        // System.exit(2);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        User Alesha = new User("log1","pas1",1);
        User Misha = new User("log2","pas2",2);
        ArrayList<User> Users = new ArrayList();
        Users.add(Alesha);
        Users.add(Misha);
        Resource res1 = new Resource("B",
                new ArrayList<Integer>(Arrays.asList(Alesha.userId,Misha.userId)),
                new ArrayList<String>(Arrays.asList(Roles.EXECUTE.toString(),Roles.READ.toString())));
        Resource res2 = new Resource("C",
                new ArrayList<Integer>(Arrays.asList(Alesha.userId,Misha.userId)),
                new ArrayList<String>(Arrays.asList(Roles.WRITE.toString(),Roles.READ.toString())));
        ArrayList<Resource> Res = new ArrayList();
        Res.add(res1);
        Res.add(res2);
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
enum Roles{
    READ, WRITE, EXECUTE
}