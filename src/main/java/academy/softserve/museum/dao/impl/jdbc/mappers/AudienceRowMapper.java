package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Audience;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for creating Audience from ResultSet.
 */
public class AudienceRowMapper implements RowMapper<Audience> {

    /**
     * Method for creating Audience from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  creating Audience.
     * @return Audience created from ResultSet.
     */
    @Override
    public Audience mapRow(ResultSet resultSet) {
        Audience audience = new Audience();

        try {
            audience.setId(resultSet.getLong("audience_id"));
            audience.setName(resultSet.getString("audience_name"));
            return audience;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
