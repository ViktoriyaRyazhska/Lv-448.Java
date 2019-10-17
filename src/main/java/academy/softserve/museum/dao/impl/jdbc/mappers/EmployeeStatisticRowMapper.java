package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.statistic.EmployeeStatistic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EmployeeStatisticRowMapper implements RowMapper<EmployeeStatistic> {

    @Override
    public EmployeeStatistic mapRow(ResultSet resultSet) {
        EmployeeStatistic statistic = new EmployeeStatistic();
        Map<Employee, Integer> workTimeMap = new HashMap<>();
        EmployeeRowMaper employeeRowMaper = new EmployeeRowMaper();

        try {
            do {
                workTimeMap.put(employeeRowMaper.mapRow(resultSet), resultSet.getInt("excursion_time"));
            } while (resultSet.next());
            statistic.setWorkTimeMap(workTimeMap);

            return statistic;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
