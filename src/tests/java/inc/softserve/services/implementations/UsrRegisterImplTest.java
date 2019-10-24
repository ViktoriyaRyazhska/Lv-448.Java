package inc.softserve.services.implementations;

import inc.softserve.dao.implementations.*;
import inc.softserve.dao.interfaces.*;
import inc.softserve.database.ConnectDb;
import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;
import inc.softserve.security.JavaNativeSaltGen;
import inc.softserve.security.SaltGen;
import inc.softserve.services.intefaces.UsrRegisterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UsrRegisterImplTest {

    private static BookingDao bookingDao;
    private static RoomDao roomDao;
    private static HotelDao hotelDao;
    private static CityDao cityDao;
    private static CountryDao countryDao;
    private static UsrDao usrDao;
    private static VisaDao visaDao;
    private static Connection connection;
    private static UsrRegisterService usrRegisterService;
    private static UsrDto usrDto;
    private static VisaDto visaDto;


    @BeforeAll
    static void init() {
        connection = ConnectDb.connectBase();
        countryDao = new CountryDaoJdbc(connection);
        cityDao = new CityDaoJdbc(connection, countryDao);
        hotelDao = new HotelDaoJdbc(connection, cityDao);
        roomDao = new RoomDaoJdbc(connection, hotelDao);
        usrDao = new UsrDaoJdbc(connection);
        bookingDao = new BookingDaoJdbc(connection, usrDao, roomDao, hotelDao);
        visaDao = new VisaDaoJdbc(connection, usrDao, countryDao);
        SaltGen saltGen = new JavaNativeSaltGen();
        usrRegisterService = new UsrRegisterImpl(saltGen, usrDao, visaDao, countryDao, connection);
    }

    @Test
    void register() {
    }

    @Test
    void loginInTest() {
        Usr user = usrDao
                .findByEmail("q@gmail.com")
                .orElseThrow();
        Usr expected = usrRegisterService.login("q@gmail.com","q");
        assertEquals(expected, user);
    }

    @Test
    void loginInInvalidDataTest() {
        Usr user = usrDao
                .findByEmail("s1@gmail.com")
                .orElseThrow();
        Usr expected = usrRegisterService.login("s1@gmail.com","123");
        assertEquals(expected, user);
    }

    @Test
    void loginInInvalidPassTest() {
        String actual = null;
        Usr expected = usrRegisterService.login("1igor@gmail.com","ha");
        assertEquals(expected, actual);
    }

    @Test
    void validateInvalidDataTest() {
        Map<String, String> actual = usrRegisterService.validateData("", "");
        Map<String, String> expected = new HashMap<>();
        expected.put("email", "Given email is not valid!");
        assertEquals(expected, actual);
    }

    @Test
    void validateDataTest() {
        Map<String, String> actual = usrRegisterService.validateData("igor@gmail.com", "1234");
        Map<String, String> expected = new HashMap<>();
        assertEquals(expected, actual);
    }

    @Test
    void validateInvalidEmailTest() {
        Map<String, String> actual = usrRegisterService.validateData("igormail.com", "1234");
        Map<String, String> expected = new HashMap<>();
        expected.put("email", "Given email is not valid!");
        assertEquals(expected, actual);
    }

    @Test
    void loginOut() {
    }

    @Test
    void initUsrDto() {
//        UsrDto actual = usrRegisterService.initUsrDto("Sasha", "Sasha", "ig@orgmail.kom",
//                "+380964504813", usrRegisterService.generateDate("2014-10-12").toString(), "pass");
//        usrDto = new UsrDto("ig@orgmail.kom","+380964504813", "pass", "Sasha",
//                "Sasha", usrRegisterService.generateDate("2014-10-12"));
//        assertEquals(usrDto, actual);
    }

    @Test
    void initVisaDto() {
        VisaDto actual = usrRegisterService.initVisaDto("USA","2014-10-12", "2014-10-12", "2337");
        visaDto = new VisaDto("2337", usrRegisterService.generateDate("2014-10-12"),
                usrRegisterService.generateDate("2014-10-12"), "USA");
        assertEquals(visaDto, actual);
    }
}