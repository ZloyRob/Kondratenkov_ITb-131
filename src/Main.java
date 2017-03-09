import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start Program");
        AAAService aa = new AAAService();
        User Alesha;
        aa.addHash(Alesha = new User("log1", aa.addSal(), "pas1", 1));
        User Misha;
        aa.addHash(Misha = new User("log2", aa.addSal(), "pas2", 2));
        ArrayList<User> Users = new ArrayList();
        Users.add(Alesha);
        Users.add(Misha);
        Resource res1 = new Resource("B",
                Arrays.asList(Alesha.userId, Misha.userId),
                Arrays.asList(Enums.Roles.EXECUTE.toString(), Enums.Roles.READ.toString()));
        Resource res2 = new Resource("C",
                Arrays.asList(Alesha.userId, Misha.userId),
                Arrays.asList(Enums.Roles.WRITE.toString(), Enums.Roles.READ.toString()));
        ArrayList<Resource> Res = new ArrayList();
        Res.add(res1);
        Res.add(res2);
        ArrayList<Accounting> journal = new ArrayList(); //список всех сессий
        UserInput userInput = new UserInput();
        Validator valid = new Validator();
        valid.allocation(userInput, args);
        for (int i = 1; i < args.length; i += 2) { //Вывод значений параметров
            System.out.println(args[i]);
        }
        boolean p1 = valid.authentication(Users, userInput);
        if (p1 == true) {
            System.out.println("Authentication +");
        }
        ;
        boolean p2 = valid.authorization(p1, userInput, Res);
        if (p2 == true) {
            System.out.println("Authorization +");
        }
        ;
        boolean p3 = valid.accouting(p2, userInput, journal);
        if (p3 == true) {
            System.out.println("Accouting +");
            System.out.println(aa.toString(journal, 0));
        }
        ;


    }
}

