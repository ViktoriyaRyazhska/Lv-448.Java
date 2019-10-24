package inc.softserve.dto.on_responce;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
public class BookingRespDto {

    private final Long bookingId;
    private final String bookedFrom;
    private final String bookedTill;

    @Builder
    public BookingRespDto(Long bookingId, LocalDate bookedFrom, LocalDate bookedTill) {
        this.bookingId = bookingId;
        if (bookedFrom == null){
            this.bookedFrom = "";
        } else {
            this.bookedFrom = Date.from(bookedFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()).toGMTString();
        }
        if (bookedTill == null){
            this.bookedTill = "";
        } else {
            this.bookedTill = Date.from(bookedTill.atStartOfDay(ZoneId.systemDefault()).toInstant()).toGMTString();
        }
    }
}
