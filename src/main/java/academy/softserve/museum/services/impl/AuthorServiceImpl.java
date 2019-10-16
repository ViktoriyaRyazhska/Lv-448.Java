package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.impl.jdbc.JdbcAuthorDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcExhibitDao;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.services.AuthorService;
import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private JdbcAuthorDao jdbcAuthorDao;
    private JdbcExhibitDao jdbcExhibitDao;

    @Override
    public List<Exhibit> findExhibitsByAuthor(Author author) {
        return jdbcAuthorDao.findExhibitsByAuthor(author);
    }

    @Override
    public boolean addExhibitAuthor(Author author, Exhibit exhibit) {
        if ((jdbcAuthorDao.findById(author.getId()).isPresent()) &&
                (jdbcExhibitDao.findById(exhibit.getId()).isPresent())) {
            return false;
        } else {
            jdbcAuthorDao.addExhibitAuthor(author, exhibit);
            return true;
        }
    }

    @Override
    public boolean deleteExhibitAuthor(Author author, Exhibit exhibit) {
        if ((jdbcAuthorDao.findById(author.getId()).isPresent()) &&
                (jdbcExhibitDao.findById(exhibit.getId()).isPresent())) {
            jdbcAuthorDao.deleteExhibitAuthor(author, exhibit);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean save(Author objectToSave) {
        if (jdbcAuthorDao.findByFullName(objectToSave.getFirstName(), objectToSave.getLastName())
                .isPresent()) {
            return false;
        } else {
            jdbcAuthorDao.save(objectToSave);
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (jdbcAuthorDao.findById(id).isPresent()) {
            jdbcAuthorDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return jdbcAuthorDao.findById(id);
    }

    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        return jdbcAuthorDao.findByFullName(fName, lName);
    }

    @Override
    public List<Author> findAll() {
        return jdbcAuthorDao.findAll();
    }

    @Override
    public boolean update(Author newObject) {
        if (jdbcAuthorDao.findByFullName(newObject.getFirstName(), newObject.getLastName())
                .isPresent()) {
            jdbcAuthorDao.update(newObject);
            return true;
        } else {
            return false;
        }

    }
}
