package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testConnectDB {
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECT_URL = "jdbc:mysql://localhost:3306/" +
            "test_library?" +
            "useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&" +
            "serverTimezone=UTC";

    static Connection testConnection() {
        try {
            Connection connection = DriverManager.getConnection(CONNECT_URL, USER, PASSWORD);
            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
