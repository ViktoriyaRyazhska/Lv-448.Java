package inc.softserve.dao.implementations;

import inc.softserve.dao.db_test_utils.FieldChecked;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.datebase.ConnectDb;
import inc.softserve.entities.Usr;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsrDaoJdbcTest {

    private static Connection connection;
    private static UsrDao usrDaoJdbc;

    @BeforeAll
    static void init(){
        connection = ConnectDb.connectBase();
        usrDaoJdbc = new UsrDaoJdbc(connection);
    }

    @AfterAll
    static void cleanup() throws SQLException {
        connection.close();
    }

    @Test
    void save() {
        Usr usr = new Usr();
        usr.setEmail("231423@gmail.com");
        usr.setPhoneNumber("+1321");
        usr.setPasswordHash("hash");
        usr.setSalt("salt");
        usr.setFirstName("firstname");
        usr.setLastName("lastname");
        usr.setRole(Usr.Role.CLIENT);
        usr.setBirthDate(LocalDate.now());
        usr = usrDaoJdbc.save(usr);
        assertNotNull(usr.getId());
        try (PreparedStatement prepStat = connection.prepareStatement("DELETE FROM users WHERE id = ?")){
            prepStat.setLong(1, usr.getId());
            assertEquals(1, prepStat.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findAll() {
        Set<Usr> users = usrDaoJdbc.findAll();
        assertEquals(5, users.size());
        List<Predicate<Field>> skipFields = List.of(
                field -> field.getName().equals("birthDate"),
                field -> field.getName().equals("visitedCountries"),
                field -> field.getName().equals("visas"),
                field -> field.getName().equals("bookings")
        );
        FieldChecked.assertFieldValues(users.stream(),
                skipFields.stream().reduce(x -> true, Predicate::and),
                Assertions::assertNotNull);
    }

    @Test
    void findById() {
        Usr usr = usrDaoJdbc
                .findById((long) 3)
                .orElseThrow();
        Long expectedId = (long) 3;
        Long actualId = usr.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    void findByEmail() {
        Usr usr = usrDaoJdbc
                .findByEmail("neo@gmail.com")
                .orElseThrow();
        String expected = "neo@gmail.com";
        String actual = usr.getEmail();
        assertEquals(expected, actual);
    }
}