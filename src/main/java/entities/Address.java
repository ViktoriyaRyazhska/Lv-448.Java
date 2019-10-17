package entities;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private long id;
    private String city;
    private String street;
    private long buildingNumber;
    private long apartment;
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                buildingNumber == address.buildingNumber &&
                apartment == address.apartment &&
                city.equals(address.city) &&
                street.equals(address.street) &&
                users.equals(address.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, buildingNumber, apartment, users);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", apartment=" + apartment +
                ", users=" + users +
                '}';
    }
}
