package inc.softserve.dao.interfaces;

import inc.softserve.entities.Booking;
import inc.softserve.entities.stats.RoomBooking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookingDao {

    Booking save(Booking booking);

    Set<Booking> findAll();

    Optional<Booking> findById(Long bookingId);

    Set<Booking> findBookingsByRoomId(Long roomId);

    Set<Booking> findBookingsByRoomIdAndDate(Long roomId, LocalDate fromDate);

    Set<Booking> findBookingsByUsrId(Long usrId);

    List<RoomBooking> showAllFutureBookings(Long hotelId);

    List<RoomBooking> findByRoomIdAndDate(Long roomId, LocalDate fromDate);
}
