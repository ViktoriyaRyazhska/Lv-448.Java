package inc.softserve.dao.implementations;

import inc.softserve.dao.db_test_utils.InitDataBase;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.entities.Country;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
import inc.softserve.utils.rethrowing_lambdas.ThrowingLambdas;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VisaDaoJdbcTest {

    private static Connection connection;
    private static UsrDao usrDao;
    private static CountryDao countryDao;
    private static VisaDaoJdbc visaDaoJdbc;

    @BeforeAll
    static void init() throws SQLException {
        connection = InitDataBase.createAndPopulate();
        usrDao = new UsrDaoJdbc(connection);
        countryDao = new CountryDaoJdbc(connection);
        visaDaoJdbc = new VisaDaoJdbc(connection, usrDao, countryDao);
    }

    @AfterAll
    static void destroy() throws SQLException {
        connection.close();
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
        Set<Visa> visas = visaDaoJdbc.findAll();
        assertEquals(6, visas.size());
        visas.stream()
                .flatMap(visa -> Stream.of(visa
                        .getClass()
                        .getFields())
                        .map(ThrowingLambdas.function(field -> {
                            field.setAccessible(true);
                            return field.get(visa); }))
                )
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void findById() {
        Visa visa = visaDaoJdbc
                .findById((long) 3)
                .orElseThrow();
        Long expected = (long) 3;
        Long actual = visa.getId();
        assertEquals(expected, actual);
    }

    @Test
    void findByVisaNumber() {
        Visa visa = visaDaoJdbc
                .findByVisaNumber("345665659")
                .orElseThrow();
        Long expectedId = (long) 4;
        Long actualId = visa.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    void findVisasByCountryId() {
        Set<Visa> visas = visaDaoJdbc.findVisasByCountryId((long) 2);
        Long expectedSize = (long) 3;
        Long actualSize = (long) visas.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void issuedVisas() {
        int expectedNumber = 2;
        int actualNumber = visaDaoJdbc.issuedVisas((long) 3);
        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void usrHasVisas() {
    }
}