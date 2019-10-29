package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Audience;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

class AudienceRowMapperTest {

    @Test
    void rowMapSuccess() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Audience audience = new Audience(1, "Test");

        Mockito.when(resultSet.getLong("audience_id")).thenReturn(audience.getId());
        Mockito.when(resultSet.getString("audience_name")).thenReturn(audience.getName());

        JdbcDaoTest.assertAudienceEquals(audience, new AudienceRowMapper().mapRow(resultSet));
    }

    @Test
    void rowMapFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);

        Assertions.assertThrows(RuntimeException.class, () -> new AudienceRowMapper().mapRow(resultSet));
    }
}
