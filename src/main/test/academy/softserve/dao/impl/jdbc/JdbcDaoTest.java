package academy.softserve.dao.impl.jdbc;

import academy.softserve.museum.dao.impl.jdbc.*;
import academy.softserve.museum.database.DaoFactory;
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

    JdbcAuthorDao jdbcAuthorDao() {
        return (JdbcAuthorDao) DaoFactory.authorDao();
    }

    JdbcAudienceDao jdbcAudienceDao() {
        return (JdbcAudienceDao) DaoFactory.audienceDao();
    }

    JdbcExhibitDao jdbcExhibitDao() {
        return (JdbcExhibitDao) DaoFactory.exhibitDao();
    }

    JdbcEmployeeDao jdbcEmployeeDao() {
        return (JdbcEmployeeDao) DaoFactory.employeeDao();
    }

    JdbcExcursionDao jdbcExcursionDao() {
        return (JdbcExcursionDao) DaoFactory.excursionDao();
    }

    JdbcTimetableDao jdbcTimetableDao() {
        return (JdbcTimetableDao) DaoFactory.timetableDao();
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

    void assertAudienceEquals(Audience a, Audience b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getId(), b.getId());
    }

    void assertAudienceEquals(List<Audience> a, List<Audience> b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertAudienceEquals(a.get(i), b.get(i));
        }
    }

    void assertEmployeeEquals(Employee a, Employee b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getPosition(), b.getPosition());
        assertEquals(a.getPassword(), b.getPassword());
        assertEquals(a.getLogin(), b.getLogin());
        assertEquals(a.getFirstName(), b.getFirstName());
        assertEquals(a.getLastName(), b.getLastName());
    }

    void assertEmployeeEquals(List<Employee> a, List<Employee> b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertEmployeeEquals(a.get(i), b.get(i));
        }
    }

    void assertExcursionEquals(Excursion a, Excursion b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.getId(), b.getId());
        assertEquals(a.getName(), b.getName());
    }

    void assertExcursionEquals(List<Excursion> a, List<Excursion> b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertExcursionEquals(a.get(i), b.get(i));
        }
    }

    void assertTimetableEquals(Timetable a, Timetable b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEmployeeEquals(a.getEmployee(), b.getEmployee());
        assertExcursionEquals(a.getExcursion(), b.getExcursion());
        assertEquals(a.getDateStart(), b.getDateStart());
        assertEquals(a.getDateEnd(), b.getDateEnd());
    }

    void assertTimetableEquals(List<Timetable> a, List<Timetable> b) {
        assertNotNull(a);
        assertNotNull(b);
        assertEquals(a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertTimetableEquals(a.get(i), b.get(i));
        }
    }
}
