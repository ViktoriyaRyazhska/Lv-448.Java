package inc.softserve.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Booking {

    private Long id;
    private LocalDate orderDate;
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
        return Objects.equals(orderDate, booking.orderDate) &&
                Objects.equals(checkin, booking.checkin) &&
                Objects.equals(checkout, booking.checkout) &&
                Objects.equals(usr, booking.usr) &&
                Objects.equals(room, booking.room) &&
                Objects.equals(hotel, booking.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, checkin, checkout, usr, room, hotel);
    }
}
