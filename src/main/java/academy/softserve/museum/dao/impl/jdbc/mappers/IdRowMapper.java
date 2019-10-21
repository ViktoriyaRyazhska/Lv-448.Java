package academy.softserve.museum.dao.impl.jdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdRowMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet resultSet) {
        try {
            return resultSet.getInt("last_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
