package dao.interfaceDao;

import entities.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDaoInterface {
    void save(Author author);

    Optional<Author> findById(Long id);

    void update(Author author);

    List<Author> findBySurname(String surname);

    List<Author> findAll();

    List<Author> findAllSubAuthorByBookId(Long bookId);

}
