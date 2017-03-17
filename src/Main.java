import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        User johnDoe = new User("jdoe", "sup3rpaZZ", 1); //Создаем пользователей
        User janeRow = new User("jrow", "Qweqrty12", 2);
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
        boolean isAuthorization = valid.isAuthorization(resources, userInput, isAuthentication);
        valid.isAccouting(journal, userInput, isAuthorization);

    }
}



