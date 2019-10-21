package inc.softserve.dao.interfaces;

import java.util.Optional;
import java.util.Set;

public interface Read<T> {

    Set<T> findAll();

    Optional<T> findById(Long id);
}
