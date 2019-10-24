package inc.softserve.services.intefaces;

import inc.softserve.dto.on_request.BookingReqDto;

import java.time.LocalDate;

public interface BookingService {
    void book(BookingReqDto bookingReqDto, LocalDate orderDate);
}
