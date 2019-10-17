package academy.softserve.museum.dao.impl.jdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExhibitTypeStatisticMapper implements RowMapper<Map<String, Integer>> {
    private String statisticField;

    public ExhibitTypeStatisticMapper(String statisticField) {
        this.statisticField = statisticField;
    }

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
