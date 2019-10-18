package academy.softserve.museum.entities;

public class Employee extends Entity {
    private String firstName;
    private String lastName;
    private EmployeePosition position;
    private String login;
    private String password;
    private Audience audience;
    private String audienceName;

    public Employee(long id, String firstName, String lastName, EmployeePosition position, String login, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.login = login;
        this.password = password;
    }

    public Employee(String firstName, String lastName, EmployeePosition position, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.login = login;
        this.password = password;
    }

    public Employee() {
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public Employee setPosition(EmployeePosition position) {
        this.position = position;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Employee setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Employee setPassword(String password) {
        this.password = password;
        return this;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position=" + position +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", audience=" + audience +
                '}';
    }

    public String getAudienceName() {
        return this.audience.getName();
    }
}
