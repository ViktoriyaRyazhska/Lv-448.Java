package dao.interfaceDao;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {

    void save(T t);

    void deleteById(Long id);

    Optional<T> findById(Long id);

}

