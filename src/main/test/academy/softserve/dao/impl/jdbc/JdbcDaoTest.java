package academy.softserve.dao.impl.jdbc;

import academy.softserve.museum.dao.impl.jdbc.JdbcAudienceDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcAuthorDao;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JdbcDaoTest {
    private static final String CREATE_TABLES = "/sql/create_db.sql";
    private static final String DROP_TABLES = "/sql/drop_db.sql";
    private static final String FILL_TABLES = "/sql/insert_test_db.sql";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(System.getenv("test_db_url"),
                    System.getenv("test_db_user"), System.getenv("test_db_password"));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void dropTables() {
        executeSql(loadSql(DROP_TABLES));
    }

    void createTables() {
        executeSql(loadSql(CREATE_TABLES));
    }

    void fillTables() {
        executeSql(loadSql(FILL_TABLES));
    }

    JdbcAuthorDao jdbcAuthorDao() {
        return new JdbcAuthorDao(connection);
    }

    private void executeSql(String sqlFilepath) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlFilepath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadSql(String filepath) {
        StringBuilder sql = new StringBuilder();
        String row;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(JdbcDaoTest.class.getResourceAsStream(filepath)))) {
            while ((row = in.readLine()) != null) {
                sql.append(row);
            }

            return sql.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void assertAuthorEquals(Author a, Author b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getFirstName(), b.getFirstName());
        assertEquals(a.getLastName(), b.getLastName());
        assertEquals(a.getId(), b.getId());
    }

    void assertAuthorEquals(List<Author> a, List<Author> b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertAuthorEquals(a.get(i), b.get(i));
        }
    }

    void assertExhibitEquals(Exhibit a, Exhibit b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getMaterial(), b.getMaterial());
        assertEquals(a.getTechnique(), b.getTechnique());
        assertEquals(a.getType(), b.getType());
        assertEquals(a.getId(), b.getId());
    }

    void assertExhibitEquals(List<Exhibit> a, List<Exhibit> b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertExhibitEquals(a.get(i), b.get(i));
        }
    }
}
