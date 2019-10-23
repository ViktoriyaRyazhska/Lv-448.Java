package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.statistic.ExcursionStatistic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used for creating ExcursionStatistic from ResultSet.
 */
public class ExcursionStatisticRowMapper implements RowMapper<ExcursionStatistic> {

    /**
     * Method for creating ExcursionStatistic from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  creating ExcursionStatistic.
     * @return ExcursionStatistic created from ResultSet.
     */
    @Override
    public ExcursionStatistic mapRow(ResultSet resultSet) {
        ExcursionStatistic statistic = new ExcursionStatistic();
        Map<Excursion, Integer> excursionCountMap = new HashMap<>();
        ExcursionRowMapper rowMapper = new ExcursionRowMapper();

        try {
            do {
                excursionCountMap.put(rowMapper.mapRow(resultSet), resultSet.getInt("excursion_count"));
            } while (resultSet.next());

            statistic.setExcursionCountMap(excursionCountMap);

            return statistic;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
