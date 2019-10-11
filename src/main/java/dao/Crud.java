package dao;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {

        int save(T t);

        int delete(T t);

        int deleteById(long id);

        Optional<T> findById(long id);

        List<T> findAll();
    }

