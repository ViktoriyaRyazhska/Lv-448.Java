package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GroupedExhibitMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Audience audience = new Audience(1, "Test");
        List<Exhibit> exhibits = Collections.singletonList(
                new Exhibit(1, ExhibitType.PAINTING, "Test", "Test", "Test"));
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        mockResultSetAudience(resultSet, audience);
        mockResultSetExhibit(resultSet, exhibits.get(0));

        Map<Audience, List<Exhibit>> actual = new GroupedExhibitMapper().mapRow(resultSet);

        for (Map.Entry<Audience, List<Exhibit>> e : actual.entrySet()) {
            JdbcDaoTest.assertAudienceEquals(audience, e.getKey());
            JdbcDaoTest.assertExhibitEquals(e.getValue(), exhibits);
        }
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> new GroupedExhibitMapper().mapRow(resultSet));
    }

    void mockResultSetAudience(ResultSet resultSetMock, Audience audience) throws SQLException {
        Mockito.when(resultSetMock.getLong("audience_id")).thenReturn(audience.getId());
        Mockito.when(resultSetMock.getString("audience_name")).thenReturn(audience.getName());
    }

    void mockResultSetExhibit(ResultSet resultSetMock, Exhibit exhibit) throws SQLException {
        Mockito.when(resultSetMock.getLong("exhibit_id")).thenReturn(exhibit.getId());
        Mockito.when(resultSetMock.getString("exhibit_type")).thenReturn(exhibit.getType().toString());
        Mockito.when(resultSetMock.getString("exhibit_technique")).thenReturn(exhibit.getTechnique());
        Mockito.when(resultSetMock.getString("exhibit_material")).thenReturn(exhibit.getMaterial());
        Mockito.when(resultSetMock.getString("exhibit_name")).thenReturn(exhibit.getName());
    }
}