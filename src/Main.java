import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.exit;

/**
 * Created by Вадим on 06.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Start Program");
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
        ArrayList<Accounting>Jur=new ArrayList();
        UserInput usIn = new UserInput();
        Validator valid = new Validator();
        valid.Allocation(usIn,args);
        for (int i = 1; i < args.length; i+=2) {
            System.out.println(args[i]);
        }
        boolean p1 = valid.Authentication(Users,usIn);
        if (p1 == true) System.out.println("Authentication +");
        boolean p2 = valid.Authorization(p1,usIn,Res);
        if (p2 == true) System.out.println("Authorization +");
    }
}
enum Roles{
    READ, WRITE, EXECUTE
}