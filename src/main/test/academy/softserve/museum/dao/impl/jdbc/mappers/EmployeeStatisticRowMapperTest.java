package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeStatisticRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Employee employee = new Employee(1, "First", "First",
                EmployeePosition.MANAGER, "First", "First");
        Map<Employee, Integer> workTimeMap = new HashMap<>();
        Map<Employee, Integer> excursionCount = new HashMap<>();

        workTimeMap.put(employee, 120);
        excursionCount.put(employee, 2);

        EmployeeStatistic expected = new EmployeeStatistic();
        expected.setExcursionCount(excursionCount);
        expected.setWorkTimeMap(workTimeMap);

        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getInt("excursion_time")).thenReturn(120);
        Mockito.when(resultSet.getInt("excursion_count")).thenReturn(2);
        mockResultSetEmployee(resultSet, employee);

        EmployeeStatistic actual = new EmployeeStatisticRowMapper().mapRow(resultSet);

        for(Map.Entry<Employee, Integer> e : actual.getExcursionCount().entrySet()){
            assertEquals(2, e.getValue());
            JdbcDaoTest.assertEmployeeEquals(employee, e.getKey());
        }

        for(Map.Entry<Employee, Integer> e : actual.getWorkTimeMap().entrySet()){
            assertEquals(120, e.getValue());
            JdbcDaoTest.assertEmployeeEquals(employee, e.getKey());
        }
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getInt(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getLong(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> new EmployeeStatisticRowMapper().mapRow(resultSet));
    }

    private void mockResultSetEmployee(ResultSet resultSetMock, Employee employee) throws SQLException {
        Mockito.when(resultSetMock.getString("employee_first_name")).thenReturn(employee.getFirstName());
        Mockito.when(resultSetMock.getString("employee_last_name")).thenReturn(employee.getLastName());
        Mockito.when(resultSetMock.getString("employee_login")).thenReturn(employee.getLogin());
        Mockito.when(resultSetMock.getString("employee_password")).thenReturn(employee.getPassword());
        Mockito.when(resultSetMock.getString("employee_position")).thenReturn(employee.getPosition().toString());
        Mockito.when(resultSetMock.getLong("employee_id")).thenReturn(employee.getId());
    }
}