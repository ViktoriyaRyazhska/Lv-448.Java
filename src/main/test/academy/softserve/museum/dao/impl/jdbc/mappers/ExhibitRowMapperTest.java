package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ExhibitRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Exhibit exhibit = new Exhibit(1, ExhibitType.PAINTING, null, "Test", "Test");
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong("exhibit_id")).thenReturn(exhibit.getId());
        Mockito.when(resultSet.getString("exhibit_type")).thenReturn(exhibit.getType().toString());
        Mockito.when(resultSet.getString("exhibit_technique")).thenReturn(exhibit.getTechnique());
        Mockito.when(resultSet.getString("exhibit_name")).thenReturn(exhibit.getName());

        JdbcDaoTest.assertExhibitEquals(exhibit, new ExhibitRowMapper().mapRow(resultSet));
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> new ExhibitRowMapper().mapRow(resultSet));
    }
}