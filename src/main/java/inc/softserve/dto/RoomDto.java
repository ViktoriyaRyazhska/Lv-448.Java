package inc.softserve.dto;

import inc.softserve.entities.Room;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomDto {

    private final Room room;
    private final LocalDate bookedFrom;
    private final LocalDate bookedTo;

    @Builder
    public RoomDto(Room room, LocalDate bookedFrom, LocalDate bookedTo) {
        this.room = room;
        this.bookedFrom = bookedFrom;
        this.bookedTo = bookedTo;
    }
}
