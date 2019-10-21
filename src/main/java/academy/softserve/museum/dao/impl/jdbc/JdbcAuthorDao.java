package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AuthorRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.IdRowMapper;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JdbcAuthorDao implements AuthorDao {

    private static JdbcAuthorDao instance;
    private final Connection connection;
    private ExhibitDao exhibitDao;

    private JdbcAuthorDao(Connection connection) {
        this.connection = connection;
    }

    public static JdbcAuthorDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new JdbcAuthorDao(connection);
        }

        return instance;
    }

    @Override
    public void addAuthor(Author author, Exhibit exhibit) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) VALUES(?, ?)";

        JdbcUtils.update(connection, ADD_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    @Override
    public void deleteAuthor(Author author, Exhibit exhibit) {
        String DELETE_EXHIBIT_AUTHOR = "DELETE FROM autor_exhibit WHERE autor_id = ? and exhibit_id = ?";

        JdbcUtils.update(connection, DELETE_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        String FIND_AUTHOR_BY_FULL_NAME =
                "SELECT id AS author_id, first_name AS author_first_name," +
                        "last_name AS author_last_name " +
                        "FROM autors WHERE first_name = ? and last_name = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUTHOR_BY_FULL_NAME, new AuthorRowMapper(), fName, lName);
    }

    @Override
    public List<Author> findByExhibit(Exhibit exhibit) {
        String FIND_AUTHORS_BY_EXHIBIT_ID =
                "SELECT a.id AS author_id, a.first_name AS author_first_name, a.last_name AS author_last_name " +
                        "FROM autors AS a " +
                        "INNER JOIN autor_exhibit AS ae " +
                        "ON a.id = ae.autor_id " +
                        "WHERE ae.exhibit_id = ? ";

        return JdbcUtils.query(connection, FIND_AUTHORS_BY_EXHIBIT_ID, new AuthorRowMapper(), exhibit.getId());
    }

    @Override
    public Author loadForeignFields(Author author) {
        author.setExhibits(exhibitDao.findByAuthor(author));

        return author;
    }

    @Override
    public long save(Author objectToSave) {
        String SAVE_AUTHOR = "INSERT INTO autors(first_name, last_name) VALUES(?, ?)";

        JdbcUtils.update(connection, SAVE_AUTHOR, objectToSave.getFirstName(), objectToSave.getLastName());

        return getLastSavedObjectId();
    }

    @Override
    public void deleteById(long id) {
        String DELETE_AUTHOR = "DELETE FROM autors WHERE id = ?";

        JdbcUtils.update(connection, DELETE_AUTHOR, id);
    }

    @Override
    public Optional<Author> findById(long id) {
        String FIND_AUTHOR_BY_ID = "SELECT id AS author_id, first_name AS author_first_name," +
                "last_name AS author_last_name FROM autors WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUTHOR_BY_ID, new AuthorRowMapper(), id);
    }

    @Override
    public List<Author> findAll() {
        String FIND_ALL_AUTHORS = "SELECT id AS author_id, first_name AS author_first_name," +
                "last_name AS author_last_name FROM autors";

        return JdbcUtils.query(connection, FIND_ALL_AUTHORS, new AuthorRowMapper());
    }

    @Override
    public int update(Author newObject) {
        String UPDATE_AUTHOR = "UPDATE autors set first_name = ?, last_name = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_AUTHOR, newObject.getFirstName(), newObject.getLastName(), newObject.getId());
    }

    private long getLastSavedObjectId() {
        String QUERY = "SELECT MAX(id) AS last_id FROM autors";

        return JdbcUtils.queryForObject(connection, QUERY, new IdRowMapper()).
                orElseThrow(() -> new RuntimeException("Can't get last saved object id"));
    }

    public void setExhibitDao(ExhibitDao exhibitDao) {
        this.exhibitDao = exhibitDao;
    }
}
