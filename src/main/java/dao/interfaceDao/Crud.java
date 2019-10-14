package dao.interfaceDao;

public interface Crud<T> {

        void save(T t);

        void deleteById(Long id);

    }

