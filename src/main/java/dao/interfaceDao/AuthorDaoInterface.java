package dao.interfaceDao;

import entities.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDaoInterface {
    void save(Author author);

    Optional<Author> findById(Long id);

    void update(Author author);

    Optional<Author> findBySurname(String surname);

    List<Author> findAll();

}
