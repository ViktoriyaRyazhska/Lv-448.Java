package inc.softserve.dao.interfaces;

import inc.softserve.entities.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookingDao {

    Set<Booking> findAll();

    Optional<Booking> findById(Long bookingId);

    List<LocalDate> showAllFuture(Long hotelId);
}
