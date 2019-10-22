package inc.softserve.dto;

import inc.softserve.entities.Room;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
public class RoomDto {

    private final Room room;
    private final String bookedFrom;
    private final String bookedTo;

    @Builder
    public RoomDto(Room room, LocalDate bookedFrom, LocalDate bookedTo) {
        this.room = room;
        if (bookedFrom == null){
            this.bookedFrom = "";
        } else {
            this.bookedFrom = Date.from(bookedFrom.atStartOfDay(ZoneId.systemDefault()).toInstant())
                    .toGMTString();
        }
        if (bookedTo == null){
            this.bookedTo = "";
        } else {
            this.bookedTo = Date.from(bookedTo.atStartOfDay(ZoneId.systemDefault()).toInstant())
                    .toGMTString();
        }
    }
}
