package academy.softserve.museum.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface that contains CRUD methods.
 * Every Dao interface should implement this to
 * perform CRUD operations.
 */
public interface Crud<T> {
    /**
     * Method for saving objects in database.
     *
     * @param objectToSave object, that must be saved.
     * @return id of last saved object.
     */
    long save(T objectToSave);

    /**
     * Method for deleting object by id.
     *
     * @param id id of object, that must be deleted.
     */
    void deleteById(long id);

    /**
     * Method, that returns object wrapped in Optional by id.
     *
     * @param id object's id.
     * @return object wrapped in Optional that has id field value
     * the same as id parameter value.
     * If there is no object with such id value
     * it returns Optional.empty
     */
    Optional<T> findById(long id);

    /**
     * Method, that returns all objects of T type from database
     *
     * @return list of objects of T type from database
     */
    List<T> findAll();

    /**
     * Method, that updates given object in database.
     *
     * @param newObject object to update
     * @return changed rows count in database.
     */
    int update(T newObject);

}
