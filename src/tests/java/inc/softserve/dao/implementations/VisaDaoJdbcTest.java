package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.datebase.ConnectDb;
import inc.softserve.entities.Country;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void save(){
        Usr usr = new Usr();
        Country country = new Country();
        country.setId((long) 4);
        usr.setId((long) 2);
        Visa visa = new Visa();
        visa.setVisaNumber("q4123fadsf");
        visa.setUsr(usr);
        visa.setCountry(country);
        visa.setIssued(LocalDate.of(2010, 10, 10));
        visa.setExpirationDate(LocalDate.of(2011, 11, 11));
        visa = visaDaoJdbc.save(visa);
        assertNotNull(visa.getId());
        try (PreparedStatement prepStat = connection.prepareStatement("DELETE FROM travel_visas where id = ?")) {
            prepStat.setLong(1, visa.getId());
            assertEquals(1, prepStat.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        visaDaoJdbc.findByVisaNumber("675654567").ifPresent(System.out::println);
    }

    @Test
    void findVisasByCountryId() {
        visaDaoJdbc.findVisasByCountryId((long) 2).forEach(System.out::println);
    }

    @Test
    void issuedVisas() {
        System.out.println(visaDaoJdbc.issuedVisas("Italy"));
        System.out.println(visaDaoJdbc.issuedVisas((long) 2));
    }

    @Test
    void usrHasVisas() {
        System.out.println(visaDaoJdbc.usrHasVisas("user@gmail.com"));
        System.out.println(visaDaoJdbc.usrHasVisas((long) 2));
    }
}