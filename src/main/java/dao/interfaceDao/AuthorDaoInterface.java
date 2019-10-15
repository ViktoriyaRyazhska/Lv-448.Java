package dao.interfaceDao;

import entities.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDaoInterface extends Crud<Author> {
    List<Author> findAll();
    Optional<Author> findBySurname();

}
