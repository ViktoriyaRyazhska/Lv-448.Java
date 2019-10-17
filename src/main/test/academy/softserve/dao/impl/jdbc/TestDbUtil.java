//package academy.softserve.dao.impl.jdbc;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class TestDbUtil {
//    private static final Connection connection;
//    private static final String CREATE_TABLES = "create_tables.sql";
//    private static final String DROP_TABLES = "drop_db.sql";
//
//    public static void executeSql(String sqlFilepath) {
//        try (Statement statement = connection.createStatement()) {
//            statement.execute(loadSql(sqlFilepath));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        executeSql(DROP_TABLES);
//        executeSql(CREATE_TABLES);
//    }
//
//    private static String loadSql(String filepath) {
//        StringBuilder sql = new StringBuilder();
//        String row;
//
//        try (BufferedReader in = new BufferedReader(new InputStreamReader(TestDbUtil.class.getResourceAsStream(filepath)))) {
//            while ((row = in.readLine()) != null) {
//                sql.append(row);
//            }
//
//            return sql.toString();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
