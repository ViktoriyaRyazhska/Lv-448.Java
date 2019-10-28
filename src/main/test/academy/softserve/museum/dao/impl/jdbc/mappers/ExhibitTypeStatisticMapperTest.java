package academy.softserve.museum.dao.impl.jdbc.mappers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExhibitTypeStatisticMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Map<String, Integer> map = new HashMap<>();
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        String type = "Test";
        int expectedNumber = 1;

        map.put(type, expectedNumber);

        Mockito.when(resultSet.getString(type)).thenReturn(type);
        Mockito.when(resultSet.getInt(Mockito.anyString())).thenReturn(expectedNumber);

        assertEquals(map, new ExhibitTypeStatisticMapper(type).mapRow(resultSet));
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getInt(Mockito.anyString())).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> new ExhibitTypeStatisticMapper("Test").mapRow(resultSet));
    }
}