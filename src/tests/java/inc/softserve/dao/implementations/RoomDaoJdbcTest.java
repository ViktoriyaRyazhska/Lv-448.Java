package inc.softserve.dao.implementations;

import inc.softserve.dao.db_test_utils.FieldChecked;
import inc.softserve.dao.db_test_utils.InitDataBase;
import inc.softserve.dao.interfaces.*;
import inc.softserve.entities.Hotel;
import inc.softserve.entities.Room;
import inc.softserve.entities.stats.RoomStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        int actualSize = roomDao.findAll().size();
        int expected = 54;
        assertEquals(expected, actualSize);
    }

    @Test
    void findById() {
        int expectedChamberNumber = 234;
        int actual = roomDao.findById(10L).orElseThrow().getChamberNumber();
        assertEquals(expectedChamberNumber, actual);
    }

    @Test
    void findByHotelId() {
        Set<Room> rooms = roomDao.findByHotelId(4L);
        int expectedSize = 3;
        int actual = rooms.size();
        assertEquals(expectedSize, actual);
        Set<Integer> chambers = new HashSet<>(Set.of(234, 232, 123));
        chambers.removeAll(
                rooms.stream()
                .map(Room::getChamberNumber)
                .collect(Collectors.toSet())
        );
        assertEquals(0, chambers.size());
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
        List<RoomStats> stats = roomDao.calcStats(1L,
                LocalDate.of(2019, 10, 1),
                LocalDate.of(2019, 12, 1));
        int expectedSize = 1;
        int actualSize = stats.size();
        assertEquals(expectedSize, actualSize);
        FieldChecked.assertFieldValues(stats.stream(), x -> true, Assertions::assertNotNull);
        RoomStats roomStats = stats.stream().findFirst().orElseThrow();
        assertEquals(Long.valueOf(1), roomStats.getHotel().getId());
        assertEquals(301, roomStats.getChamberNumber());
        assertEquals(1, roomStats.getRoomCount());
    }
}