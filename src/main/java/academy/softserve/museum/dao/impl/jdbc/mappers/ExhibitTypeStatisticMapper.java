package academy.softserve.museum.dao.impl.jdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class used for creating Exhibit statistic by given parameter from ResultSet.
 */
public class ExhibitTypeStatisticMapper implements RowMapper<Map<String, Integer>> {

    /**
     * Parameter used for counting statistic.
     */
    private String statisticField;

    /**
     * Constructor, that get parameter for counting statistic.
     *
     * @param statisticField parameter for counting statistic.
     */
    public ExhibitTypeStatisticMapper(String statisticField) {
        this.statisticField = statisticField;
    }

    /**
     * Method for creating statistic by given parameter from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  creating statistic.
     * @return statistic created from ResultSet.
     */
    @Override
    public Map<String, Integer> mapRow(ResultSet resultSet) {
        String statisticFieldValue;
        Map<String, Integer> statistic = new LinkedHashMap<>();

        try {
            do {
                statisticFieldValue = resultSet.getString(statisticField);

                statistic.put(statisticFieldValue, resultSet.getInt("number"));
            } while (resultSet.next());

            return statistic;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
