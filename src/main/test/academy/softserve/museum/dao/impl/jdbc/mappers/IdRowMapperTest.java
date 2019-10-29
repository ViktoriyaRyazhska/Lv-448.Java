package academy.softserve.museum.dao.impl.jdbc.mappers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class IdRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Long id = 1L;
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong("last_id")).thenReturn(id);

        assertEquals(id, new IdRowMapper().mapRow(resultSet));
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong("last_id")).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> new IdRowMapper().mapRow(resultSet));
    }
}