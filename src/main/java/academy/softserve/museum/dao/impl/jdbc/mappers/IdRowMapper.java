package academy.softserve.museum.dao.impl.jdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for getting Id from ResultSet.
 */
public class IdRowMapper implements RowMapper<Long> {

    /**
     * Method for getting id from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  getting id.
     * @return id taken from ResultSet.
     */
    @Override
    public Long mapRow(ResultSet resultSet) {
        try {
            return resultSet.getLong("last_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
