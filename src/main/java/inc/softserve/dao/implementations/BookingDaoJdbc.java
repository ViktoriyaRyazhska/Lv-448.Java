package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.entities.Booking;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class BookingDaoJdbc implements BookingDao {

    private final Connection connection;

    public BookingDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Set<Booking> findAll() {
        return null;
    }

    @Override
    public Optional<Booking> findById(Long bookingId) {
        return null;
    }

    @Override
    public List<LocalDate> showAllFuture(Long hotelId) {
        String query = "SELECT * FROM rooms " +
                "INNER JOIN bookings " +
                "ON bookings.room_id = rooms.id " +
                "WHERE hotel_id = ? AND bookings.checkin > CURDATE() AND bookings.checkout < CURDATE();";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, hotelId);
            // TODO
            return null;
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
