package inc.softserve.entities;

import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
public class Hotel {
    private Long id;
    private String hotelName;
    private String street;
    private String streetNumber;
    private Stars stars;
    private City city;
    private Set<Room> rooms;
    private Set<Booking> bookedRooms;

    public enum Stars{
        _3, _4, _5
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return hotelName.equals(hotel.hotelName) &&
                street.equals(hotel.street) &&
                streetNumber.equals(hotel.streetNumber) &&
                city.equals(hotel.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelName, street, streetNumber, city);
    }
}
