package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Exhibit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class used for creating groped Exhibits by Audience from ResultSet.
 */
public class GroupedExhibitMapper implements RowMapper<Map<Audience, List<Exhibit>>> {

    /**
     * Method for creating groped Exhibits from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  creating groped Exhibits.
     * @return groped Exhibits created from ResultSet.
     */
    @Override
    public Map<Audience, List<Exhibit>> mapRow(ResultSet resultSet) {
        Map<Audience, List<Exhibit>> groupedAudiences = new HashMap<>();
        AudienceRowMapper audienceRowMapper = new AudienceRowMapper();
        ExhibitRowMapper exhibitRowMapper = new ExhibitRowMapper();
        Exhibit exhibit;
        Audience audience;

        try {
            do {
                audience = audienceRowMapper.mapRow(resultSet);
                exhibit = exhibitRowMapper.mapRow(resultSet);

                if (groupedAudiences.containsKey(audience)) {
                    groupedAudiences.get(audience).add(exhibit);
                } else {
                    groupedAudiences
                            .put(audience, new ArrayList<>(Collections.singletonList(exhibit)));
                }
            } while (resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupedAudiences;
    }

}
