package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.entities.Room;
import inc.softserve.entities.stats.RoomStats;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Slf4j
public class RoomDaoJdbc implements RoomDao {

    private static final String TABLE_NAME = "rooms";

    private final Connection connection;
    private final HotelDao hotelDao;

    public RoomDaoJdbc(Connection connection, HotelDao hotelDao) {
        this.connection = connection;
        this.hotelDao = hotelDao;
    }

    /**
     * Lazy implementation. Collection (Set<Booking>) will not be brought.
     * @return all rooms in the database.
     */
    @Override
    public Set<Room> findAll() {
        String query = "SELECT * FROM rooms";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            ResultSet resultSet = prepStat.executeQuery();
            return extractRooms(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Lazy implementation. Collection (Set<Booking>) will not be brought.
     * @param roomId - an identificator of a room
     * @return - not empty optional if a room with given id exists
     */
    @Override
    public Optional<Room> findById(Long roomId) {
        String query = "SELECT * FROM rooms WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, roomId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractRooms(resultSet).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Maps resultset to room stream
     * @param rs - resultset containing data from rooms table.
     * @return - room stream
     */
    private Stream<Room> extractRooms(ResultSet rs) throws SQLException {
        Stream.Builder<Room> builder = Stream.builder();
        while (rs.next()){
            Room room = new Room();
            room.setId(rs.getLong("id"));
            room.setChamberNumber(rs.getInt("chamber_number"));
            room.setLuxury(Room.Luxury.valueOf(rs.getString("luxury")));
            room.setBedrooms(Room.Bedrooms.valueOf(rs.getString("bedrooms")));
            room.setHotel(hotelDao
                    .findById(rs.getLong("hotel_id"))
                    .orElseThrow());
            builder.accept(room);
        }
        return builder.build();
    }

    /**
     * Lazy implementation. Collection (Set<Booking>) will not be brought.
     * @param hotelId - an identificator of a hotel.
     * @return - not empty set if a hotel with given id exists and there are rooms attached to it.
     */
    @Override
    public Set<Room> findByHotelId(Long hotelId) {
        String query = "SELECT * FROM rooms WHERE hotel_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, hotelId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractRooms(resultSet).collect(Collectors.toUnmodifiableSet());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Lazy implementation. Collection (Set<Booking>) will not be brought.
     * @param cityId - an identificator of a hotel.
     * @return - not empty set if a city with given id exists and there are rooms attached to it.
     */
    @Override
    public Set<Room> findByCityId(Long cityId){
        String query = "SELECT * FROM rooms WHERE city_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, cityId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractRooms(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lazy implementation. Collection (Set<Booking>) will not be brought.
     * @param cityId - an identificator of a city
     * @param from - start date
     * @param till - end date
     * @return not empty set if a city with given id exists and there are bookings that overlaps with given time period.
     */
    @Override
    public Set<Room> findBookedRoomsByCityIdAndTimePeriod(Long cityId, LocalDate from, LocalDate till){
        String query = "SELECT * FROM rooms " +
                "INNER JOIN bookings " +
                "ON rooms.id = bookings.room_id " +
                "WHERE rooms.city_id = ? " +
                "AND ((? BETWEEN checkin AND checkout) " +
                "OR (? BETWEEN checkin AND checkout) " +
                "OR (? < checkin AND ? > checkout))";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, cityId);
            prepStat.setDate(2, Date.valueOf(from));
            prepStat.setDate(3, Date.valueOf(till));
            prepStat.setDate(4, Date.valueOf(from));
            prepStat.setDate(5, Date.valueOf(till));
            ResultSet resultSet = prepStat.executeQuery();
            return extractRooms(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return list of RoomStats
     * @param hotelId - an identificator of a hotel
     * @param startPeriod - start period
     * @param endPeriod - end period
     * @return - statistics of rooms within given time period.
     */
    @Override
    public List<RoomStats> calcStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod) {
        String query = "SELECT chamber_number, COUNT(*) AS room_count, rooms.hotel_id FROM rooms " +
                "INNER JOIN bookings " +
                "ON bookings.room_id = rooms.id " +
                "WHERE rooms.hotel_id = ? " +
                "AND ((? BETWEEN checkin AND checkout) " +
                "OR (? BETWEEN checkin AND checkout) " +
                "OR (? < checkin AND ? > checkout)) " +
                "GROUP BY chamber_number, rooms.hotel_id";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, hotelId);
            prepStat.setDate(2, Date.valueOf(startPeriod));
            prepStat.setDate(3, Date.valueOf(endPeriod));
            prepStat.setDate(4, Date.valueOf(startPeriod));
            prepStat.setDate(5, Date.valueOf(endPeriod));
            ResultSet resultSet = prepStat.executeQuery();
            return extractRoomStats(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Maps date calculated by the database to stream
     * @param rs - resultset containing columns, hotel_id, chamber_number, room_count.
     * @return - RoomStats stream.
     */
    private Stream<RoomStats> extractRoomStats(ResultSet rs) throws SQLException {
        Stream.Builder<RoomStats> builder = Stream.builder();
        while (rs.next()){
            RoomStats roomStats = RoomStats.builder()
                    .hotel(hotelDao
                            .findById(rs.getLong("hotel_id"))
                            .orElseThrow())
                    .chamberNumber(rs.getInt("chamber_number"))
                    .roomCount(rs.getInt("room_count"))
                    .build();
            builder.accept(roomStats);
        }
        return builder.build();
    }
}
