package academy.softserve.museum.services;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TimetableService {

    void save(Timetable objectToSave);

    boolean deleteById(long id);

    Optional<Timetable> findById(long id);

    List<Timetable> findAll();

    boolean update(Timetable newObject);

    List<Employee> findAvailableTourGuides(Date dateStart, Date dateEnd);

    List<Excursion> findAvailableExcursions(Date dateStart, Date dateEnd);

}
