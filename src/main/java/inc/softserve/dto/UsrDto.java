package inc.softserve.dto;

import inc.softserve.entities.Usr;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsrDto {
    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

}
