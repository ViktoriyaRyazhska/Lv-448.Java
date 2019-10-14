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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userSurname, user.userSurname) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(registrationDate, user.registrationDate) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userSurname, birthday, registrationDate, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", surname='" + userSurname + '\'' +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
