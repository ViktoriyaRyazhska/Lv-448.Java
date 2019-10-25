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

    @Override
    public Set<Room> findRoomsByCityId(Long cityId){
        String query = "SELECT * FROM rooms WHERE city_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, cityId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractRooms(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Room> findAllFutureBookedRoomsByCityId(Long cityId, LocalDate from){
        String query = "SELECT * FROM rooms " +
                "INNER JOIN bookings " +
                "ON rooms.id = bookings.room_id " +
                "WHERE rooms.city_id = ? " +
                "AND bookings.checkin > ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, cityId);
            prepStat.setDate(2, Date.valueOf(from));
            ResultSet resultSet = prepStat.executeQuery();
            return extractRooms(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoomStats> calcStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod) {
        String query = "SELECT chamber_number, COUNT(*) AS room_count, rooms.hotel_id FROM rooms " +
                "INNER JOIN bookings " +
                "ON bookings.room_id = rooms.id " +
                "WHERE bookings.checkin > ? OR bookings.checkout < ? AND rooms.hotel_id = ? " +
                "GROUP BY chamber_number, rooms.hotel_id";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setDate(1, Date.valueOf(startPeriod));
            prepStat.setDate(2, Date.valueOf(endPeriod));
            prepStat.setLong(3, hotelId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractRoomStats(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

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
