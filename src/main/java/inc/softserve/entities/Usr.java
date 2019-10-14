package inc.softserve.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
public class Usr {
    private Long id;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private String salt;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDate birthDate;
    private Set<Country> visitedCountries;
    private Set<Visa> visas;
    private Set<Booking> bookings;

    public enum Role{
        CLIENT, ADMIN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usr usr = (Usr) o;
        return email.equals(usr.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
