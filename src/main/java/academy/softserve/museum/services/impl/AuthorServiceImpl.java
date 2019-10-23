package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcAuthorDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcExhibitDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
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

    public AuthorServiceImpl() {
        authorDao = DaoFactory.authorDao();
        exhibitDao = DaoFactory.exhibitDao();
    }

    public AuthorServiceImpl(JdbcAuthorDao authorDao,
            JdbcExhibitDao exhibitDao) {
        this.authorDao = authorDao;
        this.exhibitDao = exhibitDao;
    }

    @Override
    public boolean addExhibitAuthor(Author author, Exhibit exhibit) {
        if ((authorDao.findById(author.getId()).isPresent()) &&
                (exhibitDao.findById(exhibit.getId()).isPresent())) {
            throw new NotSavedException(ErrorMessage.EXHIBIT_AUTHOR_NOT_DELETED);
        } else {
            authorDao.addAuthor(author, exhibit);
            return true;
        }
    }

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

    @Override
    public boolean save(Author objectToSave) {
        if (authorDao.findByFullName(objectToSave.getFirstName(), objectToSave.getLastName())
                .isPresent()) {
            throw new NotSavedException(ErrorMessage.AUTHOR_NOT_SAVED);
        } else {
            authorDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (authorDao.findById(id).isPresent()) {
            authorDao.deleteById(id);
            return true;
        } else {
         throw new NotDeletedException(ErrorMessage.AUTHOR_NOT_DELETED);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author = authorDao.findById(id).orElse(null);
        return Optional.of(Optional.of(authorDao.loadForeignFields(author))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        Author author = authorDao.findByFullName(fName, lName).orElse(null);
            return Optional.of(Optional.of(authorDao.loadForeignFields(author))
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public List<Author> findAll() {
        List<Author> authorList = authorDao.loadForeignFields(authorDao.findAll());
        if(authorList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return authorList;
    }

    @Override
    public boolean update(Author newObject) {
        if (authorDao.findByFullName(newObject.getFirstName(), newObject.getLastName())
                .isPresent()) {
            authorDao.update(newObject);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.AUTHOR_NOT_UPDATED);
        }

    }
}
