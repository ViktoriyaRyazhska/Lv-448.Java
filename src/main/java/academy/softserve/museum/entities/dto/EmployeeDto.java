package academy.softserve.museum.entities.dto;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.EmployeePosition;

public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private EmployeePosition position;
    private Long audienceId;

    public EmployeeDto(String firstName, String lastName, String username, String password, EmployeePosition position, Long audienceId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.position = position;
        this.audienceId = audienceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public Long getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(Long audienceId) {
        this.audienceId = audienceId;
    }
}
