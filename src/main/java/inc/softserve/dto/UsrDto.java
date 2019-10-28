package inc.softserve.dto;

import lombok.Data;

@Data
public class UsrDto {
    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;

    public UsrDto() {
    }

    public UsrDto(String email, String phoneNumber, String password, String firstName, String lastName, String birthDate) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
