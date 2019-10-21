package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;

import java.util.List;
import java.util.Optional;

/**
 * Interface, that contains special methods for
 * getting / updating Author objects from / in database.
 */
public interface AuthorDao extends Crud<Author> {

    /**
     * Method for adding new Author for Exhibit.
     *
     * @param author  Author you want to add Exhibit.
     * @param exhibit Exhibit you want to add for Author
     */
    void addAuthor(Author author, Exhibit exhibit);

    /**
     * Method for deleting Exhibit's Author.
     *
     * @param author  Author which must be deleted.
     * @param exhibit Exhibit for which yow want
     *                to delete Author.
     */
    void deleteAuthor(Author author, Exhibit exhibit);

    /**
     * Method for finding Author by first and last names.
     *
     * @param fName Author's first name.
     * @param lName Author's last name.
     * @return Author object wrapped in Optional or
     * Optional.empty() if there is no Author with
     * such full name.
     */
    Optional<Author> findByFullName(String fName, String lName);

    /**
     * Method for finding all Exhibit's authors.
     *
     * @param exhibit Exhibit for which you want
     *                to find authors.
     * @return List of Authors of given exhibit.
     */
    List<Author> findByExhibit(Exhibit exhibit);

    /**
     * Method for loading and setting author's Exhibits.
     *
     * @param author Author for which you want to load and
     *               set Exhibits.
     * @return Given object with set Exhibits.
     */
    Author loadForeignFields(Author author);

    /**
     * Method for loading and setting Author's Exhibits
     * (accepts list of Authors)
     *
     * @param authors list of Authors for which elements you want
     *                to load and set Exhibits.
     * @return Given list with set Exhibits.
     */
    default List<Author> loadForeignFields(List<Author> authors) {
        authors.forEach(this::loadForeignFields);

        return authors;
    }
}
