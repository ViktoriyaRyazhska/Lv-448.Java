package inc.softserve.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Booking {

    private Long id;
    private LocalDate localDate;
    private LocalDate checkin;
    private LocalDate checkout;
    private Usr usr;
    private Room room;
    private Hotel hotel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return usr.equals(booking.usr) &&
                room.equals(booking.room) &&
                hotel.equals(booking.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usr, room, hotel);
    }
}
