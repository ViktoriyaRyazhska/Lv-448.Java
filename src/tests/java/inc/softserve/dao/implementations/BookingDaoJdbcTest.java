package inc.softserve.dao.implementations;

import inc.softserve.dao.db_test_utils.FieldChecked;
import inc.softserve.dao.db_test_utils.InitDataBase;
import inc.softserve.dao.interfaces.*;
import inc.softserve.entities.Booking;
import inc.softserve.entities.Hotel;
import inc.softserve.entities.Room;
import inc.softserve.entities.Usr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingDaoJdbcTest {

    private static Connection connection;
    private static BookingDao bookingDao;
    private static UsrDao usrDao;
    private static CountryDao countryDao;
    private static RoomDao roomDao;
    private static HotelDao hotelDao;
    private static CityDao cityDao;

    @BeforeAll
    static void init() throws SQLException {
        connection = InitDataBase.createAndPopulate();
        usrDao = new UsrDaoJdbc(connection);
        countryDao = new CountryDaoJdbc(connection);
        cityDao = new CityDaoJdbc(connection, countryDao);
        hotelDao = new HotelDaoJdbc(connection, cityDao);
        roomDao = new RoomDaoJdbc(connection, hotelDao);
        bookingDao = new BookingDaoJdbc(connection, usrDao, roomDao, hotelDao);
    }

    @Test
    void save() throws SQLException {
        Usr usr = usrDao.findById((long) 3).orElseThrow();
        Room room = roomDao.findById((long) 2).orElseThrow();
        Hotel hotel = hotelDao.findById((long) 4).orElseThrow();
        Booking booking = new Booking();
        booking.setOrderDate(LocalDate.now());
        booking.setCheckin(LocalDate.of(2022, 3, 3));
        booking.setCheckout(LocalDate.of(2022, 4, 4));
        booking.setUsr(usr);
        booking.setRoom(room);
        booking.setHotel(hotel);
        booking = bookingDao.save(booking);
        assertNotNull(booking.getId());
        try (PreparedStatement prepStat = connection.prepareStatement("DELETE FROM bookings WHERE id = ?")){
            prepStat.setLong(1, booking.getId());
            assertEquals(1, prepStat.executeUpdate());
        }
    }

    @Test
    void findAll() {
        Set<Booking> bookings = bookingDao.findAll();
        assertEquals(6, bookings.size());
        FieldChecked.assertFieldValues(bookings.stream(), x -> true, Assertions::assertNotNull);
    }

    @Test
    void findById() {
        Long expectedId = (long) 2;
        assertEquals(expectedId, bookingDao.findById((long) 2)
                .orElseThrow()
                .getId());
    }

    @Test
    void findBookingsByUsrId() {
        Long expectedSize = (long) 4;
        Long actualSize = (long) bookingDao.findBookingsByUsrId((long) 2).size();
        assertEquals(expectedSize, actualSize);
    }
}