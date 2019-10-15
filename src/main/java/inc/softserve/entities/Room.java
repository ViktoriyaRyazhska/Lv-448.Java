package inc.softserve.entities;

import lombok.Data;

import java.util.Objects;

@Data
public class Room {

    private Long id;
    private Integer chamberNumber;
    private Luxury luxury;
    private Bedrooms bedrooms;
    private Hotel hotel;
    private City city;
    private Booking booking;

    public enum Luxury{
        ECONOM, STANDARD, BUSINESS, PREMIUM
    }

    public enum Bedrooms {
        SINGLE, DOUBLE, TRIPLE, APARTMENT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return chamberNumber.equals(room.chamberNumber) &&
                hotel.equals(room.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chamberNumber, hotel);
    }
}
