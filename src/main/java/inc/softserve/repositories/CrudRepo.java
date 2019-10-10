package inc.softserve.repositories;

import java.util.Set;

public interface CrudRepo<T> {

    long save(T t);

    long delete(T t);

    long deleteById(long id);

    Set<T> findAll();

    T findById(long id);
}
