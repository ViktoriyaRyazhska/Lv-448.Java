package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.EmployeePosition;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for creating Timetable object from ResultSet.
 */
public class TimetableRowMapper implements RowMapper<Timetable> {

    /**
     * Method for creating Timetable object from ResultSet.
     *
     * @param resultSet object with needed data for
     *                  creating Timetable object.
     * @return Timetable object created from ResultSet.
     */
    @Override
    public Timetable mapRow(ResultSet resultSet) {
        Timetable timetable = new Timetable();
        Employee employee = new Employee();
        Excursion excursion = new Excursion();

        try {
            timetable.setId(resultSet.getLong("timetable_id"));
            timetable.setDateStart(new Date(resultSet.getTimestamp("date_start").getTime()));
            timetable.setDateEnd(new Date(resultSet.getTimestamp("date_end").getTime()));

            employee.setId(resultSet.getLong("employee_id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setPosition(EmployeePosition.valueOf(resultSet.getString("position")));
            employee.setPassword(resultSet.getString("password"));
            employee.setLogin(resultSet.getString("login"));
            timetable.setEmployee(employee);

            excursion.setId(resultSet.getLong("excursion_id"));
            excursion.setName(resultSet.getString("excursion_name"));
            timetable.setExcursion(excursion);

            return timetable;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
