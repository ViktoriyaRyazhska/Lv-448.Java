package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.entities.Booking;
import inc.softserve.entities.Room;
import inc.softserve.entities.stats.RoomBooking;

import java.sql.*;
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
        String query = "INSERT INTO bookings (usr_id, order_date, checkin, checkout, room_id, hotel_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepStat = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setLong(1, booking.getUsr().getId());
            prepStat.setDate(2, Date.valueOf(booking.getOrderDate()));
            prepStat.setDate(3, Date.valueOf(booking.getCheckin()));
            prepStat.setDate(4, Date.valueOf(booking.getCheckout()));
            prepStat.setLong(5, booking.getRoom().getId());
            prepStat.setLong(6, booking.getHotel().getId());
            prepStat.executeUpdate();
            try (ResultSet keys = prepStat.getGeneratedKeys()){
                if (keys.next()){
                    booking.setId(keys.getLong(1));
                }
            }
            return booking;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            builder.accept(booking);
        }
        return builder.build();
    }

    @Override
    public Set<Booking> findBookingsByUsrId(Long usrId){
        String query = "SELECT * FROM bookings WHERE usr_id = ?";
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
    public List<RoomBooking> showAllFutureBookings(Long hotelId) {
        String query = "SELECT rooms.id, rooms.chamber_number, rooms.luxury, rooms.bedrooms, rooms.hotel_id, rooms.city_id, " +
                "bookings.checkin, bookings.checkout " +
                "FROM rooms " +
                "INNER JOIN bookings " +
                "ON bookings.room_id = rooms.id " +
                "WHERE bookings.hotel_id = ? AND bookings.checkin > CURDATE()";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, hotelId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractFutureBookings(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Stream<RoomBooking> extractFutureBookings(ResultSet rs) throws SQLException {
        Stream.Builder<RoomBooking> builder = Stream.builder();
        while (rs.next()){
            RoomBooking roomBooking = RoomBooking.builder()
                    .room(roomDao
                            .findById(rs.getLong("id"))
                            .orElseThrow())
                    .checkin(rs.getDate("checkin").toLocalDate())
                    .checkout(rs.getDate("checkout").toLocalDate())
                    .build();
            builder.accept(roomBooking);
        }
        return builder.build();
    }
}
