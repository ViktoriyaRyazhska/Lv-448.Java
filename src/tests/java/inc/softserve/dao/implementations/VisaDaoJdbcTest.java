package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.datebase.ConnectDb;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class VisaDaoJdbcTest {

    private static Connection connection;
    private static UsrDao usrDao;
    private static CountryDao countryDao;
    private static VisaDaoJdbc visaDaoJdbc;

    @BeforeAll
    static void init() {
        connection = ConnectDb.connectBase();
        usrDao = new UsrDaoJdbc(connection);
        countryDao = new CountryDaoJdbc(connection);
        visaDaoJdbc = new VisaDaoJdbc(connection, usrDao, countryDao);
    }

    @Test
    void findAll() {
        visaDaoJdbc.findAll().forEach(System.out::println);
    }

    @Test
    void findById() {
        visaDaoJdbc.findById((long) 2).ifPresent(System.out::println);
    }

    @Test
    void findByVisaNumber() {
    }

    @Test
    void findVisasByCountryId() {
    }

    @Test
    void issuedVisas() {
    }

    @Test
    void testIssuedVisas() {
    }

    @Test
    void usrHasVisas() {
    }

    @Test
    void testUsrHasVisas() {
    }
}