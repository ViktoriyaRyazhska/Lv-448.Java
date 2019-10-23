package academy.softserve.museum.services;

import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    boolean addExhibitAuthor(Author author, Exhibit exhibit);

    boolean deleteExhibitAuthor(Author author, Exhibit exhibit);

    boolean save(Author objectToSave);

    boolean deleteById(long id);

    Optional<Author> findById(long id);

    Optional<Author> findByFullName(String fName, String lName);

    List<Author> findAll();

    boolean update(Author newObject);

}
