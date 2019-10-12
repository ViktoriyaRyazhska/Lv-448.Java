package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Crud<T> {

        void save(T t) throws SQLException;

        void update(Long id, T t);

        void delete(T t);

        int deleteById(Long id);

        Optional<T> findById(Long id);

        List<T> findAll();
    }

