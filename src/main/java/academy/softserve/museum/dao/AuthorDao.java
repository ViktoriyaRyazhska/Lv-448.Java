package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends Crud<Author> {

    void addExhibitAuthor(Author author, Exhibit exhibit);

    void deleteExhibitAuthor(Author author, Exhibit exhibit);

    Optional<Author> findByFullName(String fName, String lName);

    List<Author> findByExhibit(Exhibit exhibit);

    Author loadForeignFields(Author author);

    default List<Author> loadForeignFields(List<Author> authors) {
        authors.forEach(this::loadForeignFields);

        return authors;
    }
}
