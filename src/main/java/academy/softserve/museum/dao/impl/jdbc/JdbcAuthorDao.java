package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AuthorRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.IdRowMapper;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Class, that implements special methods for
 * getting / updating Author objects from / in database.
 */
public class JdbcAuthorDao implements AuthorDao {

    /**
     * this field used for implementing Singleton.
     */
    private static JdbcAuthorDao instance;
    /**
     * Connection used for interaction with database.
     */
    private final Connection connection;
    /**
     * this field used for loading and setting Exhibits for Author.
     */
    private ExhibitDao exhibitDao;

    /**
     * Constructor, that creates instance using Connection.
     *
     * @param connection Connection used for interaction with database.
     */
    private JdbcAuthorDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method for getting instance of JdbcAuthorDao.
     *
     * @param connection Connection, that used for interaction with database.
     * @return instance of JdbcAuthorDao.
     */
    public synchronized static JdbcAuthorDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new JdbcAuthorDao(connection);
        }

        return instance;
    }

    /**
     * Method for adding new Author for Exhibit.
     *
     * @param author  Author you want to add Exhibit.
     * @param exhibit Exhibit you want to add for Author
     */
    @Override
    public void addAuthor(Author author, Exhibit exhibit) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) VALUES(?, ?)";

        JdbcUtils.update(connection, ADD_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    /**
     * Method for deleting Exhibit's Author.
     *
     * @param author  Author which must be deleted.
     * @param exhibit Exhibit for which yow want
     *                to delete Author.
     */
    @Override
    public void deleteAuthor(Author author, Exhibit exhibit) {
        String DELETE_EXHIBIT_AUTHOR = "DELETE FROM autor_exhibit WHERE autor_id = ? and exhibit_id = ?";

        JdbcUtils.update(connection, DELETE_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    /**
     * Method for finding Author by first and last names.
     * Method doesn't return Author with loaded Audience.
     * To load audience you need to call loadForeignFields method.
     *
     * @param fName Author's first name.
     * @param lName Author's last name.
     * @return Author object wrapped in Optional or
     * Optional.empty() if there is no Author with
     * such full name.
     */
    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        String FIND_AUTHOR_BY_FULL_NAME =
                "SELECT id AS author_id, first_name AS author_first_name," +
                        "last_name AS author_last_name " +
                        "FROM autors WHERE first_name = ? and last_name = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUTHOR_BY_FULL_NAME, new AuthorRowMapper(), fName, lName);
    }

    /**
     * Method for finding all Exhibit's authors.
     * Method doesn't return Author with loaded Audience.
     * To load audience you need to call loadForeignFields method.
     *
     * @param exhibit Exhibit for which you want
     *                to find authors.
     * @return List of Authors of given exhibit.
     */
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

    /**
     * Method for loading and setting Author's Exhibits
     *
     * @param author Author for which you want
     *               to load and set Exhibits.
     * @return Given Author with set Exhibits.
     */
    @Override
    public Author loadForeignFields(Author author) {
        author.setExhibits(exhibitDao.findByAuthor(author));

        return author;
    }

    /**
     * Method for saving Authors in database.
     * Field exhibits will not be saved. To link
     * Author with Exhibit you need to call addAuthor().
     * To delete Exhibit's Author yoo need to call deleteAuthor()
     *
     * @param objectToSave object, that must be saved.
     * @return id of last saved object.
     */
    @Override
    public long save(Author objectToSave) {
        String SAVE_AUTHOR = "INSERT INTO autors(first_name, last_name) VALUES(?, ?)";

        JdbcUtils.update(connection, SAVE_AUTHOR, objectToSave.getFirstName(), objectToSave.getLastName());

        return getLastSavedObjectId();
    }

    /**
     * Method for deleting Author by id.
     *
     * @param id id of object, that must be deleted.
     */
    @Override
    public void deleteById(long id) {
        String DELETE_AUTHOR = "DELETE FROM autors WHERE id = ?";

        JdbcUtils.update(connection, DELETE_AUTHOR, id);
    }

    /**
     * Method, that returns object wrapped in Optional by id.
     * Method doesn't return Author with loaded Audience.
     * To load Audience you need to call loadForeignFields method.
     *
     * @param id object's id.
     * @return object wrapped in Optional that has id field value
     * the same as id parameter value.
     * If there is no object with such id value
     * it returns Optional.empty
     */
    @Override
    public Optional<Author> findById(long id) {
        String FIND_AUTHOR_BY_ID = "SELECT id AS author_id, first_name AS author_first_name," +
                "last_name AS author_last_name FROM autors WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUTHOR_BY_ID, new AuthorRowMapper(), id);
    }

    /**
     * Method, that returns all objects of T type from database.
     * Method doesn't return Author with loaded Audience.
     * To load Audience you need to call loadForeignFields method.
     *
     * @return list of Author objects from database.
     */
    @Override
    public List<Author> findAll() {
        String FIND_ALL_AUTHORS = "SELECT id AS author_id, first_name AS author_first_name," +
                "last_name AS author_last_name FROM autors";

        return JdbcUtils.query(connection, FIND_ALL_AUTHORS, new AuthorRowMapper());
    }

    /**
     * Method, that updates given object in database.
     * This method updates object in database with the same
     * id as in given object. It doesnt't update Exhibits.
     * To update Exhibits you should call addAuthor() and
     * pass Author and Exhibit.
     *
     * @param newObject object to update
     * @return changed rows count in database.
     */
    @Override
    public int update(Author newObject) {
        String UPDATE_AUTHOR = "UPDATE autors set first_name = ?, last_name = ? WHERE id = ?";

        return JdbcUtils.update(connection, UPDATE_AUTHOR, newObject.getFirstName(), newObject.getLastName(), newObject.getId());
    }

    /**
     * Method for getting last saved Author id.
     *
     * @return id of last saved Author.
     */
    private long getLastSavedObjectId() {
        String QUERY = "SELECT MAX(id) AS last_id FROM autors";

        return JdbcUtils.queryForObject(connection, QUERY, new IdRowMapper()).
                orElseThrow(() -> new RuntimeException("Can't get last saved object id"));
    }

    /**
     * Setter for setting ExhibitDao object.
     * ExhibitDao used for loading Exhibits for Author.
     *
     * @param exhibitDao ExhibitDao implementation.
     */
    public void setExhibitDao(ExhibitDao exhibitDao) {
        this.exhibitDao = exhibitDao;
    }
}
