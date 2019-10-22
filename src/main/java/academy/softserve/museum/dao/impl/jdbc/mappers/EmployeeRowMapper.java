package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for creating Employee from ResultSet.
 */
public class EmployeeRowMapper implements RowMapper<Employee> {

    /**
     * Method for creating Employee from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  creating Employee.
     * @return Employee created from ResultSet.
     */
    @Override
    public Employee mapRow(ResultSet resultSet) {
        Employee employee = new Employee();
        try {
            employee.setFirstName(resultSet.getString("employee_first_name"));
            employee.setLastName(resultSet.getString("employee_last_name"));
            employee.setLogin(resultSet.getString("employee_login"));
            employee.setPassword(resultSet.getString("employee_password"));
            employee.setPosition(EmployeePosition.valueOf(resultSet.getString("employee_position")));
            employee.setId(resultSet.getLong("employee_id"));

            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
