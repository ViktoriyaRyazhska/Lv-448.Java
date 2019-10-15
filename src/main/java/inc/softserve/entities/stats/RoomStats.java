package inc.softserve.entities.stats;

import inc.softserve.entities.Hotel;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class RoomStats {

    private final Hotel hotel;
    private final int chamberNumber;
    private final int roomCount;

    @Builder
    public RoomStats(Hotel hotel, int chamberNumber, int roomCount) {
        this.hotel = hotel;
        this.chamberNumber = chamberNumber;
        this.roomCount = roomCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomStats roomStats = (RoomStats) o;
        return chamberNumber == roomStats.chamberNumber &&
                hotel.equals(roomStats.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotel, chamberNumber);
    }
}
