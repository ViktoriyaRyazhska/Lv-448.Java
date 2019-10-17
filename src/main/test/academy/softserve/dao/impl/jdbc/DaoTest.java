package academy.softserve.dao.impl.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoTest {
    private static final String CREATE_TABLES = "create_tables.sql";
    private static final String DROP_TABLES = "drop_db.sql";
    private static final String FILL_TABLES = "insert_test_db.sql";

    static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("test_db_url"),
                    System.getenv("test_db_user"), System.getenv("test_db_password"));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void dropTables() {
        executeSql(loadSql(DROP_TABLES));
    }

    protected void createTables() {
        executeSql(loadSql(CREATE_TABLES));
    }

    protected void fillTables() {
        executeSql(loadSql(FILL_TABLES));
    }

    public void executeSql(String sqlFilepath) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(loadSql(sqlFilepath));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadSql(String filepath) {
        StringBuilder sql = new StringBuilder();
        String row;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(DaoTest.class.getResourceAsStream(filepath)))) {
            while ((row = in.readLine()) != null) {
                sql.append(row);
            }

            return sql.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
