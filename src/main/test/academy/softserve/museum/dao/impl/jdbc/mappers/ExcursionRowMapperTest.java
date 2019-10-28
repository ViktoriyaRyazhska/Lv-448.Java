package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Excursion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ExcursionRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Excursion excursion = new Excursion(1, "Test");
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong("excursion_id")).thenReturn(excursion.getId());
        Mockito.when(resultSet.getString("excursion_name")).thenReturn(excursion.getName());

        JdbcDaoTest.assertExcursionEquals(excursion, new ExcursionRowMapper().mapRow(resultSet));
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> new ExcursionRowMapper().mapRow(resultSet));
    }
}