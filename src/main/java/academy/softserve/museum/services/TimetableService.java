package academy.softserve.museum.services;

import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Excursion;
import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.entities.dto.TimetableDto;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TimetableService {

    void save(TimetableDto dto);

    boolean deleteById(long id);

    Optional<Timetable> findById(long id);

    List<Timetable> findAll();

    boolean update(Timetable newObject);

}
