package inc.softserve.dao.implementations;

import inc.softserve.datebase.ConnectDb;
import inc.softserve.entities.Usr;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsrDaoJdbcTest {

    private static Connection connection;
    private static UsrDaoJdbc usrDaoJdbc;

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
    }

    @Test
    void findById() {
    }

    @Test
    void findByEmail() {
    }
}