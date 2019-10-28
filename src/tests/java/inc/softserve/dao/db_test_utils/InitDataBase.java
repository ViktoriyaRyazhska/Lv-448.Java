package inc.softserve.dao.db_test_utils;

import inc.softserve.utils.rethrowing_lambdas.RethrowingLambdas;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class InitDataBase {

    private static Connection testConnection;

    private static synchronized Connection getTestConnection() throws SQLException {
        if (testConnection == null || testConnection.isClosed()){
            testConnection = TestConnectDb.connectBase();
        }
        return testConnection;
    }

    public static Connection createAndPopulate() throws SQLException {
        executeSqlScript(getTestConnection(), "/test_travel_agency.sql");
        executeSqlScript(getTestConnection(), "/setup_db.sql");
        return testConnection;
    }

    private static void executeSqlScript(Connection connection, String relativePath) {
        InputStream initScript = InitDataBase.class.getResourceAsStream(relativePath);
        try (Scanner scanner = new Scanner(initScript)) {
            scanner.useDelimiter(";");
            scanner.tokens()
                    .map(String::trim)
                    .filter(s -> ! s.isBlank())
                    .map(RethrowingLambdas.function(connection::prepareStatement))
                    .forEach(RethrowingLambdas.consumer(p -> {
                        p.executeUpdate();
                        p.close();
                    }));
        }
    }
}
