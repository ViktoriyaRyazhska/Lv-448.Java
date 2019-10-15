package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AuthorRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExhibitRowMapper;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.utils.JdbcUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JdbcAuthorDao implements AuthorDao {

    private final Connection connection;

    public JdbcAuthorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Exhibit> findExhibitsByAuthor(Author author) {
        String EXHIBITS_BY_AUTHOR =
                "SELECT e.id, e.type, e.material, e.techic, e.name " +
                        "FROM exhibits as e " +
                        "INNER JOIN autor_exhibit as ae " +
                        "ON e.id = ae.exhibit_id and ae.autor_id = ?";

        return JdbcUtils.query(connection, EXHIBITS_BY_AUTHOR, new ExhibitRowMapper(), author.getId());
    }

    @Override
    public void addExhibitAuthor(Author author, Exhibit exhibit) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) VALUES(?, ?)";

        JdbcUtils.update(connection, ADD_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    @Override
    public void deleteExhibitAuthor(Author author, Exhibit exhibit) {
        String DELETE_EXHIBIT_AUTHOR = "DELETE FROM autor_exhibit WHERE autor_id = ? and exhibit_id = ?";

        JdbcUtils.update(connection, DELETE_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        String FIND_AUTHOR_BY_FULL_NAME =
                "SELECT * FROM autors WHERE first_name = ? and last_name = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUTHOR_BY_FULL_NAME, new AuthorRowMapper(), fName, lName);
    }

    @Override
    public void save(Author objectToSave) {
        String SAVE_AUTHOR = "INSERT INTO autors(first_name, last_name) VALUES(?, ?)";

        JdbcUtils.update(connection, SAVE_AUTHOR, objectToSave.getFirstName(), objectToSave.getLastName());
    }

    @Override
    public void deleteById(long id) {
        String DELETE_AUTHOR = "DELETE FROM autors WHERE id = ?";

        JdbcUtils.update(connection, DELETE_AUTHOR, id);
    }

    @Override
    public Optional<Author> findById(long id) {
        String FIND_AUTHOR_BY_ID = "SELECT * FROM autors WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_AUTHOR_BY_ID, new AuthorRowMapper(), id);
    }

    @Override
    public List<Author> findAll() {
        String FIND_ALL_AUTHORS = "SELECT * FROM autors";

        return JdbcUtils.query(connection, FIND_ALL_AUTHORS, new AuthorRowMapper());
    }

    @Override
    public void update(Author newObject) {
        String UPDATE_AUTHOR = "UPDATE autors set first_name = ?, last_name = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_AUTHOR, newObject.getFirstName(), newObject.getLastName(), newObject.getId());
    }

}
