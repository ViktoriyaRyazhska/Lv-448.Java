package inc.softserve.connectivity;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

//@Slf4j
public class ConnectDb {
    private static final String USER = System.getenv("db_user");
    private static final String PASSWORD = System.getenv("db_password");
    private static final String CONNECT_URL = "jdbc:mysql://localhost:3306/" +
            "travel_agency?" +
            "useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&" +
            "serverTimezone=UTC";

    private static Connection connection;

    public static synchronized Connection connectBase() {
        try {
            if (connection != null && ! connection.isClosed()){
                return connection;
            }
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver")
                    .getDeclaredConstructor()
                    .newInstance());
            connection = DriverManager.getConnection(CONNECT_URL, USER, PASSWORD);
//            log.info(conn.getMetaData().getDatabaseProductName());
//            log.info(conn.getMetaData().getDatabaseProductVersion());
//            log.info(conn.getMetaData().getDriverName());
//            log.info(conn.getMetaData().getDriverVersion());
            return connection;
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
