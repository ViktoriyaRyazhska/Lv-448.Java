package inc.softserve.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Visa {
    private Long id;
    private String visaNumber;
    private LocalDate issued;
    private LocalDate expirationDate;
    private Country country;
    private Usr usr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visa visa = (Visa) o;
        return visaNumber.equals(visa.visaNumber) &&
                country.equals(visa.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visaNumber, country);
    }
}
