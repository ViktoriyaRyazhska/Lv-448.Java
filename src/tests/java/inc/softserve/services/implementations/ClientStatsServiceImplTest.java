package inc.softserve.services.implementations;

import inc.softserve.dao.implementations.*;
import inc.softserve.dao.interfaces.*;
import inc.softserve.connectivity.ConnectDb;
import inc.softserve.entities.Usr;
import inc.softserve.services.intefaces.ClientStatsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientStatsServiceImplTest {

    private static BookingDao bookingDao;
    private static RoomDao roomDao;
    private static HotelDao hotelDao;
    private static CityDao cityDao;
    private static CountryDao countryDao;
    private static UsrDao usrDao;
    private static ClientStatsService clientStatsService;
    private static VisaDao visaDao;
    private static Connection connection;

    @BeforeAll
    static void init() throws SQLException {
        connection = ConnectDb.connectBase();
        countryDao = new CountryDaoJdbc(connection);
        cityDao = new CityDaoJdbc(connection, countryDao);
        hotelDao = new HotelDaoJdbc(connection, cityDao);
        roomDao = new RoomDaoJdbc(connection, hotelDao);
        usrDao = new UsrDaoJdbc(connection);
        bookingDao = new BookingDaoJdbc(connection, usrDao, roomDao, hotelDao);
        visaDao = new VisaDaoJdbc(connection, usrDao, countryDao);
        clientStatsService = new ClientStatsServiceImpl(usrDao, countryDao, visaDao, bookingDao);
    }

    @Test
    void getUserByEmailTest() {
        Usr user = usrDao
                .findByEmail("igor@gmail.com")
                .orElseThrow();
        Usr expected = clientStatsService.getUser("igor@gmail.com").get();
        assertEquals(expected, user);
    }

    @Test
    void getUserByInvalidEmailTest() throws Exception {
        Optional<Usr> user = usrDao
                .findByEmail("i@gmail.com");
        Optional<Usr> expected = clientStatsService.getUser("i@gmail.com");
        assertEquals(expected, user);
    }
}