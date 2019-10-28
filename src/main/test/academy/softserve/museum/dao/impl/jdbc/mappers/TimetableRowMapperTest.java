package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

class TimetableRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Employee employee = new Employee(1, "Test", "Test", EmployeePosition.MANAGER, "Test", "Test");
        Excursion excursion = new Excursion(1, "Test");
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Timetable expected = new Timetable(1, employee, excursion, new Date(100), new Date(500));

        Mockito.when(resultSet.getLong("timetable_id")).thenReturn(expected.getId());
        Mockito.when(resultSet.getTimestamp("date_start")).thenReturn(new Timestamp(expected.getDateStart().getTime()));
        Mockito.when(resultSet.getTimestamp("date_end")).thenReturn(new Timestamp(expected.getDateEnd().getTime()));

        mockResultSetEmployee(employee, resultSet);
        mockResultSetExcursion(excursion, resultSet);

        JdbcDaoTest.assertTimetableEquals(expected, new TimetableRowMapper().mapRow(resultSet));
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getTimestamp(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getLong(Mockito.any())).thenThrow(SQLException.class);

        Assertions.assertThrows(RuntimeException.class, () -> new TimetableRowMapper().mapRow(resultSet));
    }

    private void mockResultSetEmployee(Employee employee, ResultSet resultSetMock) throws SQLException {
        Mockito.when(resultSetMock.getLong("employee_id")).thenReturn(employee.getId());
        Mockito.when(resultSetMock.getString("first_name")).thenReturn(employee.getFirstName());
        Mockito.when(resultSetMock.getString("last_name")).thenReturn(employee.getLastName());
        Mockito.when(resultSetMock.getString("position")).thenReturn(employee.getPosition().toString());
        Mockito.when(resultSetMock.getString("password")).thenReturn(employee.getPassword());
        Mockito.when(resultSetMock.getString("login")).thenReturn(employee.getLogin());
    }

    private void mockResultSetExcursion(Excursion excursion, ResultSet resultSetMock) throws SQLException {
        Mockito.when(resultSetMock.getLong("excursion_id")).thenReturn(excursion.getId());
        Mockito.when(resultSetMock.getString("excursion_name")).thenReturn(excursion.getName());
    }
}