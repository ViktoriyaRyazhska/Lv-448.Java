package inc.softserve.datebase;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// @Slf4j
public class ConnectDb {
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECT_URL = "jdbc:mysql://localhost:3306/" +
            "travel_agency?" +
            "useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&" +
            "serverTimezone=UTC";

    public static Connection connectBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(CONNECT_URL, USER, PASSWORD);
      //      log.info(conn.getMetaData().getDatabaseProductName());
      //      log.info(conn.getMetaData().getDatabaseProductVersion());
      //      log.info(conn.getMetaData().getDriverName());
      //      log.info(conn.getMetaData().getDriverVersion());
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
      //      log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
