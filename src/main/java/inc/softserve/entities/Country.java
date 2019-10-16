package inc.softserve.entities;

import lombok.Data;

import java.util.*;

@Data
public class Country {
    private Long id;
    private String country;
    private Set<Usr> visitors;
    private Set<Visa> issuedVisas;
    private Set<City> cities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country1 = (Country) o;
        return country.equals(country1.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country);
    }
}
