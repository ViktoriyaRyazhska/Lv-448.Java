package inc.softserve.dao.implementations;

import inc.softserve.dao.db_test_utils.FieldChecked;
import inc.softserve.dao.db_test_utils.InitDataBase;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.entities.Country;
import inc.softserve.entities.Usr;
import inc.softserve.entities.Visa;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class VisaDaoJdbcTest {

    private static Connection connection;
    private static UsrDao usrDao;
    private static CountryDao countryDao;
    private static VisaDaoJdbc visaDaoJdbc;

    @Mock
    UsrDao usrDaoMock;

    @BeforeEach
    void testMock(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test(){
        when(usrDaoMock.findAll()).thenReturn(Collections.singleton(new Usr()));
        System.out.println(usrDaoMock.findAll());
    }

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
        country.setId(4L);
        usr.setId(4L);
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
        FieldChecked.assertFieldValues(visas.stream(), x -> true, Assertions::assertNotNull);
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
        actualNumber = visaDaoJdbc.issuedVisas("Spain");
        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void usrHasVisas() {
        int expected = 2;
        int actual = visaDaoJdbc.usrHasVisas((long) 4);
        assertEquals(expected, actual);
        actual = visaDaoJdbc.usrHasVisas("neo@gmail.com");
        assertEquals(actual, expected);
    }
}