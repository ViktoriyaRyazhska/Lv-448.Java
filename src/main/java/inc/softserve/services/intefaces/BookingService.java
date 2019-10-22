package inc.softserve.services.intefaces;

import inc.softserve.dto.BookingDto;
import inc.softserve.entities.Booking;
import inc.softserve.entities.Hotel;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface BookingService {
    void book(BookingDto bookingDto, LocalDate orderDate);
}
