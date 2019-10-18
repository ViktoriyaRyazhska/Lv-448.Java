package dao.interfaceDao;

import entities.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDaoInterface extends Crud<Author> {

    Optional<Author> findBySurname(String surname);

    List<Author> findAll();

    }
