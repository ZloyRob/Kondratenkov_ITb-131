import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start Program");
        AAAService aa = new AAAService();
        User JohnDoe; //Создаем пользователей
        aa.addHash(JohnDoe = new User("jdoe", aa.addSal(), "sup3rpaZZ", 1));
        User JaneRow;
        aa.addHash(JaneRow = new User("jrow", aa.addSal(), "Qweqrty12", 2));
        ArrayList<User> Users = new ArrayList<>(); //Создаем коллекцию пользователй
        Users.add(JohnDoe);
        Users.add(JaneRow);
        Resource res1 = new Resource("a", JohnDoe.userId, Enums.Roles.READ.toString()); //Создаем ресурсы
        Resource res2 = new Resource("a.b", JohnDoe.userId, Enums.Roles.WRITE.toString());
        Resource res3 = new Resource("a.b.c", JaneRow.userId, Enums.Roles.EXECUTE.toString());
        Resource res4 = new Resource("a.bc", JohnDoe.userId, Enums.Roles.EXECUTE.toString());
        ArrayList<Resource> Res = new ArrayList<>(); //Создаем коллекцию ресурсов
        Res.add(res1);
        Res.add(res2);
        Res.add(res3);
        Res.add(res4);
        ArrayList<Accounting> journal = new ArrayList<>(); //список всех сессий
        UserInput userInput = new UserInput();
        Validator valid = new Validator();
        valid.allocation(userInput, args);
        boolean p1 = valid.authentication(Users, userInput);
        if (p1) {
            System.out.println("Authentication +");
        }

        boolean p2 = valid.authorization(p1, userInput, Res);
        if (p2) {
            System.out.println("Authorization +");
        }

        boolean p3 = valid.accouting(p2, userInput, journal);
        if (p3) {
            System.out.println("Accouting +");
            for (Accounting jour:journal) {
                System.out.println(jour.toString());
            }

        }


    }
}

