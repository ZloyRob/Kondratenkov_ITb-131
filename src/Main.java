import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        User johnDoe = new User("jdoe", "sup3rpaZZ",7 ); //Создаем пользователей
        User janeRow = new User("jrow", "Qweqrty12", 9);
        ArrayList<User> users = new ArrayList<>(); //Создаем коллекцию пользователй
        users.add(johnDoe);
        users.add(janeRow);

        Resource res1 = new Resource("a", johnDoe.userId, "READ", 1); //Создаем ресурсы
        Resource res2 = new Resource("a.b", johnDoe.userId, "WRITE",2);
        Resource res3 = new Resource("a.b.c", janeRow.userId, "EXECUTE",3);
        Resource res4 = new Resource("a.bc", johnDoe.userId, "EXECUTE",4);
        ArrayList<Resource> resources = new ArrayList<>(); //Создаем коллекцию ресурсов
        resources.add(res1);
        resources.add(res2);
        resources.add(res3);
        resources.add(res4);

        ArrayList<Accounting> journal = new ArrayList<>(); //список всех сессий
        UserInput userInput = new UserInput();
        Validator valid = new Validator();

        valid.allocation(userInput, args);
        try {
            Class.forName("org.h2.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:h2:./Vadim",
                    "Vadim", "123");
            Statement st = conn.createStatement();
            /*for (User user: users) {
                st.execute(String.format("Insert into User(LOGIN, PASS , SALT ) vALUES ( '%s','%s','%s')",user.login,user.pass,user.salt));
            }*/
            /*for (Resource resource: resources) {
                st.execute(String.format("Insert into RESOURCE(PATH, USERID , ROLE ) vALUES ( '%s','%s','%s')",resource.path,resource.usersId,resource.role));
            }*/
            boolean isAuthentication = valid.isAuthentication(users, userInput);
            boolean isAuthorization = valid.isAuthorization(resources, userInput, isAuthentication);
            valid.isAccouting(journal, userInput, isAuthorization);
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Accounting accounting:journal) {
                st.execute(String.format("Insert into ACCOUNTING (DS, DE , VOL, RESID ) VALUES (PARSEDATETIME('%s','yyyy-MM-dd'), PARSEDATETIME('%s','yyyy-MM-dd'),'%s','%s')", sdf.format(accounting.ds), sdf.format(accounting.de), accounting.vol, accounting.resId));
            }*/
            //ResultSet result = st.executeQuery("SELECT * FROM User");
            /*while (result.next()) {
                //System.out.println(result.getString("ID")+" "+result.getString("LOGIN")+" "+result.getString("SALT"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}



