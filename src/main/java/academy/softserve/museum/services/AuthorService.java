package academy.softserve.museum.services;

import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.dto.AuthorDto;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    /**
     * Method for adding new Author for Exhibit
     *
     * @param author Author you want to add Exhibit
     * @param exhibit Exhibit you want to add for Author
     * @return true if the add was successful
     */
    boolean addExhibitAuthor(Author author, Exhibit exhibit);

    /**
     * Method for deleting Exhibit's Author
     *
     * @param author Author which must be deleted
     * @param exhibit Exhibit for which yow want to delete Author
     * @return true if the delete was successful
     */
    boolean deleteExhibitAuthor(Author author, Exhibit exhibit);

    /**
     * Method for saving objects in database
     *
     * @return true if the save was successful
     */
    boolean save(AuthorDto authorDto);

    /**
     * Method for deleting object Author by id
     *
     * @return true if the delete was successful
     */
    boolean deleteById(long id);

    /**
     * Method, that returns object Author wrapped in Optional by id
     *
     * @return Object Audience wrapped in Optional
     */
    Optional<Author> findById(long id);

    /**
     * Method for finding Author by first and last names
     *
     * @return Author object wrapped in Optional
     */
    Optional<Author> findByFullName(String fName, String lName);

    /**
     * Method, that returns all objects of Author
     *
     * @return list of Author
     */
    List<Author> findAll();

    /**
     * Method, that updates given object Author
     *
     * @return true if the update was successful
     */
    boolean update(Author author);

}
