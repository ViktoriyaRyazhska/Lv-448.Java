package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.entities.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Slf4j
public class BookingDaoJdbc implements BookingDao {

    private static final String TABLE_NAME = "bookings";

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

    /**
     * Save the booking entity into the database
     * @param booking - booking entity
     * @return - booking entity with an identificator assigned by the database
     */
    @Override
    public Booking save(Booking booking) {
        String query = "INSERT INTO bookings (usr_id, order_date, checkin, checkout, room_id, hotel_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepStat = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setLong(1, booking.getUsr().getId());
            prepStat.setTimestamp(2, Timestamp.valueOf(booking.getOrderDate()));
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

    /**
     *
     * @return all bookings in the database
     */
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

    /**
     *
     * @param bookingId - an identificator of a booking
     * @return - not empty optional if there is a booking with given id in the database
     */
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

    /**
     *
     * @param roomId - an identificator of a room
     * @return - not empty set if a room with given id exists in the database and there bookings attached to it.
     */
    @Override
    public Set<Booking> findBookingsByRoomId(Long roomId){
        String query = "SELECT * FROM bookings WHERE room_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, roomId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractBookings(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param roomId - an identificator of a room.
     * @param fromDate - search bookings that have checkin date later then given 'fromDate'.
     * @return - not empty set if a room with given id exists and there are booking attached with checkin date later then
     * given 'fromDate'.
     */
    @Override
    public Set<Booking> findBookingsByRoomIdAndDate(Long roomId, LocalDate fromDate){
        String query = "SELECT * FROM bookings " +
                "WHERE room_id = ? AND checkin > ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, roomId);
            prepStat.setDate(2, Date.valueOf(fromDate));
            ResultSet resultSet = prepStat.executeQuery();
            return extractBookings(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param hotelId - an identificator of a hotel
     * @param fromDate - search bookings that have checkin date later then given 'fromDate'
     * @return - not empty set if a hotel with given id exists and there are booking attached with checkin date later then
     * given 'fromDate'.
     */
    @Override
    public Set<Booking> findBookingsByHotelIdAndDate(Long hotelId, LocalDate fromDate) {
        String query = "SELECT * FROM bookings " +
                "WHERE hotel_id = ? AND checkin > ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, hotelId);
            prepStat.setDate(2, Date.valueOf(fromDate));
            ResultSet resultSet = prepStat.executeQuery();
            return extractBookings(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Booking> extractBookings(ResultSet rs) throws SQLException {
        Stream.Builder<Booking> builder = Stream.builder();
        while (rs.next()){
            Booking booking = new Booking();
            booking.setId(rs.getLong("id"));
            booking.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
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

    /**
     *
     * @param usrId - an identificator of a user
     * @return - not empty set if a user with given id exists and has bookings attached to him/her.
     */
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
}
