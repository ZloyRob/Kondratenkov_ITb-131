import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.sql.Connection;


public class Main {
    private static final Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("Приложение запущено");
        DaoClass dao = new DaoClass();
        Connection dbConnection = null;

        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:file:./res/db/Vadim", "Vadim", "123");
        try {
            flyway.migrate();
        } catch (Exception e) {
            flyway.baseline();
        }

        UserInput userInput = new UserInput();
        Validator valid = new Validator();
        valid.allocation(userInput, args);

        try {
            dbConnection = dao.getDBConnection();


            boolean isAuthentication = valid.isAuthentication(dao.getUserFromDataBase(userInput, dbConnection), userInput);


            boolean isAuthorization = valid.isAuthorization(dao.getResourceFromBase(userInput, dbConnection), userInput, isAuthentication);

            Accounting journal = new Accounting();
            if (valid.isAccouting(journal, userInput, isAuthorization)) {
                dao.insertRecordIntoDataBase(dbConnection, journal);
            }
        } catch (Exception e) {
            log.debug(e);
        } finally {
            dao.closeConnection(dbConnection);
        }
        log.info("Работа приложения завершена");
    }


}



