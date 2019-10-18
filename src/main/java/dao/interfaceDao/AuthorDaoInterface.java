package dao.interfaceDao;

import entities.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDaoInterface {
    void save(Author author);

    Optional<Author> findById(Long id);

    void update(Long id, Author author);

    Optional<Author> findBySurname();

    List<Author> findAll();

}
