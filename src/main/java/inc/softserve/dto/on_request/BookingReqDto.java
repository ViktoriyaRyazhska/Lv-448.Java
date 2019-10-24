package inc.softserve.dto.on_request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingReqDto {

    private final LocalDate checkin;
    private final LocalDate checkout;
    private final Long usrId;
    private final Long roomId;
    private final Long hotelId;

    @Builder
    public BookingReqDto(LocalDate checkin, LocalDate checkout, Long usrId, Long roomId, Long hotelId) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.usrId = usrId;
        this.roomId = roomId;
        this.hotelId = hotelId;
    }
}
