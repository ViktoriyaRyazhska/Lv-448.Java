package academy.softserve.museum.services;

import academy.softserve.museum.entities.Audience;
import java.util.List;
import java.util.Optional;

public interface AudienceService {

    /**
     * Method for saving object Audience in database
     *
     * @return true if the save was successful
     */
    boolean save(Audience audience);

    /**
     * Method for deleting object Audience by id
     *
     * @return true if the delete was successful
     */
    boolean deleteById(long id);

    /**
     * Method, that returns object Audience wrapped in Optional by id
     *
     * @return Object Audience wrapped in Optional
     */
    Optional<Audience> findById(long id);

    /**
     * Method for finding Audience by audience name
     *
     * @param name of audience
     * @return Audience object wrapped in Optional
     */
    Optional<Audience> findByName(String name);

    /**
     * Method, that returns all objects of Audience
     *
     * @return list of Audience
     */
    List<Audience> findAll();

    /**
     * Method, that updates given object Audience
     *
     * @return true if the update was successful
     */
    boolean update(Audience audience);

}
