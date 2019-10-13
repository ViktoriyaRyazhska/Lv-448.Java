package inc.softserve.entities;

import lombok.Data;

import java.util.*;

@Data
public class Country {
    private Long id;
    private String country;
    private List<Usr> visitors = new ArrayList<>();
    private List<Visa> issuedVisas = new ArrayList<>();
    private List<City> cities = new ArrayList<>();

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
