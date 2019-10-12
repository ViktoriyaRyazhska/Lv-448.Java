package inc.softserve.dao;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {

    int save(T t);

    int delete(T t);

    int deleteById(Long id);

    List<T> findAll();

    Optional<T> findById(Long id);

    Optional<T> findByUniqueField(String unique);
}
