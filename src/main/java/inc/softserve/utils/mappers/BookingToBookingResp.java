package inc.softserve.utils.mappers;

import inc.softserve.dto.on_responce.BookingRespDto;
import inc.softserve.entities.Booking;

public class BookingToBookingResp {

    public static BookingRespDto map(Booking booking){
        return BookingRespDto.builder()
                .bookingId(booking.getId())
                .bookedFrom(booking.getCheckin())
                .bookedTill(booking.getCheckout())
                .build();
    }
}
