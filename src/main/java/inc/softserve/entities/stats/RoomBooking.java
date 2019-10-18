package inc.softserve.entities.stats;

import inc.softserve.entities.Room;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomBooking {

    private final Room room;
    private final LocalDate checkin;
    private final LocalDate checkout;

    @Builder
    public RoomBooking(Room room, LocalDate checkin, LocalDate checkout) {
        this.room = room;
        this.checkin = checkin;
        this.checkout = checkout;
    }
}
