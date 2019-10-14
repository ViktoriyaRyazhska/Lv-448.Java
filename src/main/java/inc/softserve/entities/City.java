package inc.softserve.entities;

import lombok.Data;

import java.util.*;

@Data
public class City {
    private Long id;
    private String city;
    private Country country;
    private Set<Hotel> hotels = new HashSet<>();
    private Set<Room> rooms; // TODO - is it good to have rooms here?

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city1 = (City) o;
        return city.equals(city1.city) &&
                country.equals(city1.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country);
    }
}
