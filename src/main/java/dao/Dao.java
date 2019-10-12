package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

        void save(T t);

        void update(Long id, T t);

        void delete(T t);

        int deleteById(Long id);

        Optional<T> findById(Long id);

        List<T> findAll();
    }

