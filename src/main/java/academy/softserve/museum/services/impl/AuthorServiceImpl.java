package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcAuthorDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcExhibitDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.dto.AuthorDto;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.AuthorService;
import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;
    private ExhibitDao exhibitDao;

    /**
     * Default constructor
     */
    public AuthorServiceImpl() {
        authorDao = DaoFactory.authorDao();
        exhibitDao = DaoFactory.exhibitDao();
    }

    /**
     * Constructor with 2 parameters
     *
     * @param authorDao
     * @param exhibitDao
     */
    public AuthorServiceImpl(JdbcAuthorDao authorDao,
            JdbcExhibitDao exhibitDao) {
        this.authorDao = authorDao;
        this.exhibitDao = exhibitDao;
    }

    /**
     * Method for adding new Author for Exhibit
     *
     * @param author Author you want to add Exhibit
     * @param exhibit Exhibit you want to add for Author
     * @return true if the add was successful
     */
    @Override
    public boolean addExhibitAuthor(Author author, Exhibit exhibit) {
        if ((authorDao.findById(author.getId()).isPresent()) &&
                (exhibitDao.findById(exhibit.getId()).isPresent())) {
            throw new NotSavedException(ErrorMessage.EXHIBIT_AUTHOR_NOT_SAVED);
        } else {
            authorDao.addAuthor(author, exhibit);
            return true;
        }
    }

    /**
     * Method for deleting Exhibit's Author
     *
     * @param author Author which must be deleted
     * @param exhibit Exhibit for which yow want to delete Author
     * @return true if the delete was successful
     */
    @Override
    public boolean deleteExhibitAuthor(Author author, Exhibit exhibit) {
        if ((authorDao.findById(author.getId()).isPresent()) &&
                (exhibitDao.findById(exhibit.getId()).isPresent())) {
            authorDao.deleteAuthor(author, exhibit);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EXHIBIT_AUTHOR_NOT_DELETED);
        }
    }

    /**
     * Method for saving objects in database
     *
     * @return true if the save was successful
     */
    @Override
    public boolean save(AuthorDto authorDto) {
        if (authorDao.findByFullName(authorDto.getFirstName(), authorDto.getLastName())
                .isPresent()) {
            throw new NotSavedException(ErrorMessage.AUTHOR_NOT_SAVED);
        } else {
            Author author = new Author(authorDto.getFirstName(), authorDto.getLastName());
            authorDao.save(author);
            return true;
        }
    }

    /**
     * Method for deleting object Author by id
     *
     * @return true if the delete was successful
     */
    @Override
    public boolean deleteById(long id) {
        if (authorDao.findById(id).isPresent()) {
            authorDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.AUTHOR_NOT_DELETED);
        }
    }

    /**
     * Method, that returns object Author wrapped in Optional by id
     *
     * @return Object Audience wrapped in Optional
     */
    @Override
    public Optional<Author> findById(long id) {
        return Optional.of(Optional.of(authorDao.loadForeignFields(authorDao.findById(id).orElse(null)))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method for finding Author by first and last names
     *
     * @return Author object wrapped in Optional
     */
    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        return Optional.of(Optional.of(authorDao.loadForeignFields(authorDao
                .findByFullName(fName, lName).orElse(null)))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    /**
     * Method, that returns all objects of Author
     *
     * @return list of Author
     */
    @Override
    public List<Author> findAll() {
        List<Author> authorList = authorDao.loadForeignFields(authorDao.findAll());
        if (authorList.isEmpty()) {
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return authorList;
    }

    /**
     * Method, that updates given object Author
     *
     * @return true if the update was successful
     */
    @Override
    public boolean update(Author author) {
        if (authorDao.findByFullName(author.getFirstName(), author.getLastName())
                .isPresent()) {
            authorDao.update(author);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.AUTHOR_NOT_UPDATED);
        }

    }
}
