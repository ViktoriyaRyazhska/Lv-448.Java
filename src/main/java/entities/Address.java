package entities;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private Long id;
    private String city;
    private String street;
    private Long buildingNumber;
    private Long apartment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getId().equals(address.getId()) &&
                getCity().equals(address.getCity()) &&
                getStreet().equals(address.getStreet()) &&
                getBuildingNumber().equals(address.getBuildingNumber()) &&
                getApartment().equals(address.getApartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCity(), getStreet(), getBuildingNumber(), getApartment());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", apartment=" + apartment +
                '}';
    }
}
