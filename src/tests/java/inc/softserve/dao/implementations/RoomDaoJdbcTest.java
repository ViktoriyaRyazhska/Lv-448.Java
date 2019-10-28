package inc.softserve.dao.implementations;

import inc.softserve.dao.db_test_utils.InitDataBase;
import inc.softserve.dao.interfaces.*;
import inc.softserve.entities.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

class RoomDaoJdbcTest {

    private static BookingDao bookingDao;
    private static RoomDao roomDao;
    private static HotelDao hotelDao;
    private static CityDao cityDao;
    private static CountryDao countryDao;
    private static UsrDao usrDao;

    @BeforeAll
    static void init() throws SQLException {
        Connection connection = InitDataBase.createAndPopulate();
        countryDao = new CountryDaoJdbc(connection);
        cityDao = new CityDaoJdbc(connection, countryDao);
        hotelDao = new HotelDaoJdbc(connection, cityDao);
        roomDao = new RoomDaoJdbc(connection, hotelDao);
        usrDao = new UsrDaoJdbc(connection);
        bookingDao = new BookingDaoJdbc(connection, usrDao, roomDao, hotelDao);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByHotelId() {
    }

    @Test
    void findRoomsByCityId() {
        Set<Room> rooms = roomDao.findByCityId(1L);
        rooms.forEach(r -> r.setBooking(bookingDao.findBookingsByRoomIdAndDate(r.getId(), LocalDate.now())));
        rooms.forEach(System.out::println);
        System.out.println();
        rooms.stream()
                .flatMap(r -> r.getBooking().stream())
                .forEach(System.out::println);
    }

    @Test
    void calcStats() {
    }
}