package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Employee employee = new Employee(1, "Test", "Test", EmployeePosition.MANAGER, "Test", "Test");
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getString("employee_first_name")).thenReturn(employee.getFirstName());
        Mockito.when(resultSet.getString("employee_last_name")).thenReturn(employee.getLastName());
        Mockito.when(resultSet.getString("employee_login")).thenReturn(employee.getLogin());
        Mockito.when(resultSet.getString("employee_password")).thenReturn(employee.getPassword());
        Mockito.when(resultSet.getString("employee_position")).thenReturn(employee.getPosition().toString());
        Mockito.when(resultSet.getLong("employee_id")).thenReturn(employee.getId());

        JdbcDaoTest.assertEmployeeEquals(employee, new EmployeeRowMapper().mapRow(resultSet));
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getLong(Mockito.anyString())).thenThrow(SQLException.class);

        Assertions.assertThrows(RuntimeException.class, () -> new EmployeeRowMapper().mapRow(resultSet));
    }

}
