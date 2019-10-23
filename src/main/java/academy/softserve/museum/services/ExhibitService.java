package academy.softserve.museum.services;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import academy.softserve.museum.entities.dto.ExhibitDto;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExhibitService {

    /**
     * Method for saving object Exhibit in database
     *
     * @return true if the save was successful
     */
    boolean save(ExhibitDto exhibitDto);

    /**
     * Method for deleting object by id
     *
     * @return true if the delete was successful
     */
    boolean deleteById(long id);

    /**
     * Method for deleting object Exhibit by id
     *
     * @return true if the delete was successful
     */
    Optional<Exhibit> findById(long id);

    /**
     * Method for finding Exhibit by name
     *
     * @param name of Exhibit
     * @return Exhibit object wrapped in Optional
     */
    Optional<Exhibit> findByName(String name);

    /**
     * Method, that returns all objects of Exhibit
     *
     * @return list of Exhibit
     */
    List<Exhibit> findAll();

    /**
     * Method, that updates given object Exhibit
     *
     * @return true if the update was successful
     */
    boolean update(Exhibit exhibit);

    /**
     * Method for finding Exhibits by Author
     *
     * @param author Author of Exhibit
     * @return list of Exhibits that have given Author
     */
    List<Exhibit> findByAuthor(Author author);

    /**
     * Method for finding Exhibits by Author, that linked with Audience where Exhibit is
     *
     * @param employee Employee for which you want find Exhibits
     * @return list of Exhibits
     */
    List<Exhibit> findByEmployee(Employee employee);

    /**
     * Method for finding Exhibits by Audience
     *
     * @param audience Audience for which you want to find Exhibits
     * @return list of Exhibits located in given Audience
     */
    List<Exhibit> findByAudience(Audience audience);

    /**
     * Method for updating Audience where Exhibit located
     *
     * @param exhibit Exhibit for which you want to update Audience
     * @param audience new Audience for given Exhibit
     * @return true if the update was successful
     */
    boolean updateExhibitAudience(Exhibit exhibit, Audience audience);

    /**
     * Method for adding Author for given Exhibit
     *
     * @param exhibit Exhibit for which you want to add Author
     * @param author new Author for Exhibit
     * @return true if the add was successful
     */
    boolean addExhibitAuthor(Exhibit exhibit, Author author);

    /**
     * Method for deleting Author for given Exhibit
     *
     * @param exhibit Exhibit for which you want to delete Author
     * @param author Author to delete
     * @return true if the delete was successful
     */
    boolean deleteExhibitAuthor(Exhibit exhibit, Author author);

    /**
     * Method for finding all Exhibits grouped by Audience
     *
     * @return Map with Audience as key and List of Exhibits in that Audience as a value
     */
    Map<Audience, List<Exhibit>> findAllGroupedByAudience();

    /**
     * Method for finding statistic for all Exhibits. Statistic contains map with materials and
     * their count and map with techniques and their count.
     *
     * @return statistic.
     */
    ExhibitStatistic findStatistic();

    /**
     * Method for get list of types in enum ExhibitType
     *
     * @return types in enum ExhibitType
     */
    List<ExhibitType> getTypes();

}
