package academy.softserve.museum.dao.impl.jdbc.mappers;

import java.sql.ResultSet;

/**
 * Interface used for creating from ResultSet
 * object of type T.
 *
 * @param <T> type of object that must be
 *            created from ResultSet.
 */
public interface RowMapper<T> {

    /**
     * for creating from ResultSet
     * object of type T.
     *
     * @param resultSet object with needed data for
     *                  creating object of type T.
     * @return object created from ResultSet.
     */
    T mapRow(ResultSet resultSet);
}
