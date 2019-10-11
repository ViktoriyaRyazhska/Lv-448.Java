package connectionDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDataBase {

    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/" +
            "library?" +
            "useUnicode=true&" +
            "useSSL=false&" +
            "serverTimezone=UTC&" +
            "allowPublicKeyRetrieval=true";

    public static Connection connectBase() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}


