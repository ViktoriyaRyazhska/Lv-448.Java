package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.entities.Booking;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Slf4j
public class BookingDaoJdbc implements BookingDao {

    private final Connection connection;
    private final UsrDao usrDao;
    private final RoomDao roomDao;
    private final HotelDao hotelDao;

    public BookingDaoJdbc(Connection connection, UsrDao usrDao, RoomDao roomDao, HotelDao hotelDao) {
        this.connection = connection;
        this.usrDao = usrDao;
        this.roomDao = roomDao;
        this.hotelDao = hotelDao;
    }

    @Override
    public Booking save(Booking booking) {
        return null;
    }

    @Override
    public Set<Booking> findAll() {
        String query = "SELECT * FROM bookings";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            ResultSet resultSet = prepStat.executeQuery();
            return extractBookings(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Booking> findById(Long bookingId) {
        String query = "SELECT * FROM bookings WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, bookingId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractBookings(resultSet).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Stream<Booking> extractBookings(ResultSet rs) throws SQLException {
        Stream.Builder<Booking> builder = Stream.builder();
        while (rs.next()){
            Booking booking = new Booking();
            booking.setId(rs.getLong("id"));
            booking.setOrderDate(rs.getDate("order_date").toLocalDate());
            booking.setCheckin(rs.getDate("checkin").toLocalDate());
            booking.setCheckout(rs.getDate("checkout").toLocalDate());
            booking.setHotel(hotelDao
                    .findById(rs.getLong("hotel_id"))
                    .orElseThrow());
            booking.setRoom(roomDao
                    .findById(rs.getLong("room_id"))
                    .orElseThrow());
            booking.setUsr(usrDao
                    .findById(rs.getLong("usr_id"))
                    .orElseThrow());
        }
        return builder.build();
    }

    @Override
    public Set<Booking> findBookingsByUsrId(Long usrId){
        String query = "SELECT * FROM bookings WHERE user_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, usrId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractBookings(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LocalDate> showAllFutureBookings(Long hotelId) {
        String query = "SELECT * FROM rooms " +
                "INNER JOIN bookings " +
                "ON bookings.room_id = rooms.id " +
                "WHERE hotel_id = ? AND bookings.checkin > CURDATE() AND bookings.checkout < CURDATE();";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, hotelId);
            // TODO
            return null;
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
