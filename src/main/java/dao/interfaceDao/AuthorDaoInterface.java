package dao.interfaceDao;

import entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDaoInterface extends Crud<Author> {
    Optional<Author> findById(Long id);

    List<Author> findAll();
}
