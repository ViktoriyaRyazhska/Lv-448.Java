package inc.softserve.datebase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECT_URL = "jdbc:mysql://localhost:3306/" +
            "travel_agency?" +
            "useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&" +
            "serverTimezone=UTC";

    public static Connection connectBase()  {
        try{
            Connection conn = DriverManager.getConnection(CONNECT_URL, USER, PASSWORD);
            Class.forName("com.mysql.cj.jdbc.Driver");
            //TODO - add logging
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
