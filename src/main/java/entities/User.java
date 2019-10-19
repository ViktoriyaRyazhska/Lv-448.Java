package entities;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    private Long id;
    private String userName;
    private String userSurname;
    private LocalDate birthday;
    private LocalDate registrationDate;
    private String phoneNumber;
    private String email;
    private Address userAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                userName.equals(user.userName) &&
                userSurname.equals(user.userSurname) &&
                birthday.equals(user.birthday) &&
                registrationDate.equals(user.registrationDate) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                email.equals(user.email) &&
                Objects.equals(userAddress, user.userAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userSurname, birthday, registrationDate, phoneNumber, email, userAddress);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", userAddress=" + userAddress +
                '}';
    }
}
