import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start Program");
        AAAService aaaService = new AAAService();

        User johnDoe; //Создаем пользователей
        aaaService.addHash(johnDoe = new User("jdoe", aaaService.addSalt(), "sup3rpaZZ", 1));
        User janeRow;
        aaaService.addHash(janeRow = new User("jrow", aaaService.addSalt(), "Qweqrty12", 2));
        ArrayList<User> users = new ArrayList<>(); //Создаем коллекцию пользователй
        users.add(johnDoe);
        users.add(janeRow);

        Resource res1 = new Resource("a", johnDoe.userId, "READ"); //Создаем ресурсы
        Resource res2 = new Resource("a.b", johnDoe.userId, "WRITE");
        Resource res3 = new Resource("a.b.c", janeRow.userId, "EXECUTE");
        Resource res4 = new Resource("a.bc", johnDoe.userId, "EXECUTE");
        ArrayList<Resource> resources = new ArrayList<>(); //Создаем коллекцию ресурсов
        resources.add(res1);
        resources.add(res2);
        resources.add(res3);
        resources.add(res4);

        ArrayList<Accounting> journal = new ArrayList<>(); //список всех сессий
        UserInput userInput = new UserInput();
        Validator valid = new Validator();
        valid.allocation(userInput, args);

        boolean isAuthentication = valid.isAuthentication(users, userInput);
        if (isAuthentication) {
            System.out.println("Authentication +");
        }

        boolean isAuthorization = valid.isAuthorization(resources, userInput, isAuthentication);
        if (isAuthorization) {
            System.out.println("Authorization +");
        }

        if (valid.isAccouting(journal, userInput, isAuthorization)) {
            System.out.println("Accouting +");
            for (Accounting jour : journal) {
                System.out.println(jour.toString());
            }
        }
    }
}

