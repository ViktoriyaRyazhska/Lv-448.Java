package inc.softserve.services.intefaces;

import inc.softserve.dto.on_request.BookingReqDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BookingService {
    void book(BookingReqDto bookingReqDto, LocalDateTime orderDate);
}
