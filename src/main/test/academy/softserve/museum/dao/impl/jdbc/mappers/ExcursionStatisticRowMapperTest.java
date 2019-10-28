package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExcursionStatisticRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Map<Excursion, Integer> excursionCountMap = new HashMap<>();
        Excursion excursion = new Excursion(1, "Test");
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        excursionCountMap.put(excursion, 1);

        ExcursionStatistic expected = new ExcursionStatistic(excursionCountMap, new Date(0), new Date(100));

        mockResultSetExcursion(resultSet, excursion);
        Mockito.when(resultSet.getInt("excursion_count")).thenReturn(1);

        ExcursionStatistic actual = new ExcursionStatisticRowMapper().mapRow(resultSet);

        for(Map.Entry<Excursion, Integer> e : actual.getExcursionCountMap().entrySet()){
            JdbcDaoTest.assertExcursionEquals(e.getKey(), excursion);
        }
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getInt(Mockito.any())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.any())).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> new ExcursionStatisticRowMapper().mapRow(resultSet));
    }

    private void mockResultSetExcursion(ResultSet resultSetMock, Excursion excursion) throws SQLException {
        Mockito.when(resultSetMock.getLong("excursion_id")).thenReturn(excursion.getId());
        Mockito.when(resultSetMock.getString("excursion_name")).thenReturn(excursion.getName());
    }
}