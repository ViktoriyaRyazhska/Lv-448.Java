package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcAuthorDao;
import academy.softserve.museum.dao.impl.jdbc.JdbcExhibitDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
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
            return false;
        } else {
            authorDao.addExhibitAuthor(author, exhibit);
            return true;
        }
    }

    @Override
    public boolean deleteExhibitAuthor(Author author, Exhibit exhibit) {
        if ((authorDao.findById(author.getId()).isPresent()) &&
                (exhibitDao.findById(exhibit.getId()).isPresent())) {
            authorDao.deleteExhibitAuthor(author, exhibit);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean save(Author objectToSave) {
        if (authorDao.findByFullName(objectToSave.getFirstName(), objectToSave.getLastName())
                .isPresent()) {
            return false;
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
            return false;
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author = authorDao.findById(id).orElse(null);
        if(author != null){
            return Optional.of(authorDao.loadForeignFields(author));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        Author author = authorDao.findByFullName(fName, lName).orElse(null);
        if(author != null){
            return Optional.of(authorDao.loadForeignFields(author));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        return authorDao.loadForeignFields(authorDao.findAll());
    }

    @Override
    public boolean update(Author newObject) {
        if (authorDao.findByFullName(newObject.getFirstName(), newObject.getLastName())
                .isPresent()) {
            authorDao.update(newObject);
            return true;
        } else {
            return false;
        }

    }
}
