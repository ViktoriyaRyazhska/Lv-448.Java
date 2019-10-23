package academy.softserve.museum.services;

import academy.softserve.museum.entities.Timetable;
import academy.softserve.museum.entities.dto.TimetableDto;

import java.util.List;
import java.util.Optional;

public interface TimetableService {

    /**
     * Method for saving object Timetable in database
     *
     * @return true if the save was successful
     */
    void save(TimetableDto dto);

    /**
     * Method for deleting object by id
     *
     * @return true if the delete was successful
     */
    boolean deleteById(long id);

    /**
     * Method for deleting object Timetable by id
     *
     * @return true if the delete was successful
     */
    Optional<Timetable> findById(long id);

    /**
     * Method, that returns all objects of Timetable
     *
     * @return list of Timetable
     */
    List<Timetable> findAll();

    /**
     * Method, that updates given object Timetable
     *
     * @return true if the update was successful
     */
    boolean update(Timetable timetable);

}
