package database;

import dao.implemetsDao.AddressDao;
import dao.implemetsDao.AuthorDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DaoFactory {

    private static Connection connection;

    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/library";

    public DaoFactory() {
    }

    static {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    public static AddressDao addressDao() {
        return AddressDao.getInstance(connection);
    }

    public static AuthorDao authorDao(){
        return AuthorDao.getInstance(connection);
    }


}
