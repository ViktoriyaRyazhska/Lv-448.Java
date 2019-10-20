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


}
