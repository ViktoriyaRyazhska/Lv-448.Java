package inc.softserve.dao.db_test_utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class TestConnectDb {
    private static final String USER = System.getenv("db_user");
    private static final String PASSWORD = System.getenv("db_password");
    private static final String CONNECT_URL = "jdbc:mysql://localhost:3306/" +
            "test_travel_agency?" +
            "useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&" +
            "serverTimezone=UTC";

    static Connection connectBase() {
        try {
            Connection conn = DriverManager.getConnection(CONNECT_URL, USER, PASSWORD);
            Class.forName("com.mysql.cj.jdbc.Driver");
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
