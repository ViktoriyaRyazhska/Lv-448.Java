package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.entities.*;

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
            connection = DriverManager.getConnection(System.getenv("db_url"),
                    System.getenv("db_user"), System.getenv("db_password"));
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

    public static void assertAuthorEquals(Author a, Author b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getFirstName(), b.getFirstName());
        assertEquals(a.getLastName(), b.getLastName());
        assertEquals(a.getId(), b.getId());
    }

    public static void assertAuthorEquals(List<Author> a, List<Author> b) {
        assertListEquals(JdbcDaoTest::assertAuthorEquals, a, b);
    }

    public static void assertExhibitEquals(Exhibit a, Exhibit b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getMaterial(), b.getMaterial());
        assertEquals(a.getTechnique(), b.getTechnique());
        assertEquals(a.getType(), b.getType());
        assertEquals(a.getId(), b.getId());
    }

    public static void assertExhibitEquals(List<Exhibit> a, List<Exhibit> b) {
        assertListEquals(JdbcDaoTest::assertExhibitEquals, a, b);
    }

    public static void assertAudienceEquals(Audience a, Audience b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getId(), b.getId());
    }

    public static void assertAudienceEquals(List<Audience> a, List<Audience> b) {
        assertListEquals(JdbcDaoTest::assertAudienceEquals, a, b);
    }

    public static void assertEmployeeEquals(Employee a, Employee b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getPosition(), b.getPosition());
        assertEquals(a.getPassword(), b.getPassword());
        assertEquals(a.getLogin(), b.getLogin());
        assertEquals(a.getFirstName(), b.getFirstName());
        assertEquals(a.getLastName(), b.getLastName());
    }

    public static void assertEmployeeEquals(List<Employee> a, List<Employee> b) {
        assertListEquals(JdbcDaoTest::assertEmployeeEquals, a, b);
    }

    public static void assertExcursionEquals(Excursion a, Excursion b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getId(), b.getId());
        assertEquals(a.getName(), b.getName());
    }

    public static void assertExcursionEquals(List<Excursion> a, List<Excursion> b) {
        assertListEquals(JdbcDaoTest::assertExcursionEquals, a, b);
    }

    public static void assertTimetableEquals(Timetable a, Timetable b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEmployeeEquals(a.getEmployee(), b.getEmployee());
        assertExcursionEquals(a.getExcursion(), b.getExcursion());
        assertEquals(a.getDateStart(), b.getDateStart());
        assertEquals(a.getDateEnd(), b.getDateEnd());
    }

    public static void assertTimetableEquals(List<Timetable> a, List<Timetable> b) {
        assertListEquals(JdbcDaoTest::assertTimetableEquals, a, b);
    }

    private static <T> void assertListEquals(ObjectAssert<T> assertMethod, List<T> a, List<T> b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertMethod.assertObjectsEquals(a.get(i), b.get(i));
        }
    }
}
