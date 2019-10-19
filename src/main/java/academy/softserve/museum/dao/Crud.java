package academy.softserve.museum.dao;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {

    long save(T objectToSave);

    void deleteById(long id);

    Optional<T> findById(long id);

    List<T> findAll();

    int update(T newObject);

}
