package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AuthorRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExhibitRowMapper;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                "SELECT e.id, e.type, e.material, e.techic " +
                        "FROM exhibits as e " +
                        "INNER JOIN autor_exhibit as ae " +
                        "ON e.id = ae.exhibit_id and ae.autor_id = ?";

        List<Exhibit> exhibits = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(EXHIBITS_BY_AUTHOR)) {
            statement.setLong(1, author.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                ExhibitRowMapper rowMapper = new ExhibitRowMapper();

                while (resultSet.next()) {
                    exhibits.add(rowMapper.mapRow(resultSet));
                }
            }

            return exhibits;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addExhibitAuthor(Author author, Exhibit exhibit) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) VALUES(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(ADD_EXHIBIT_AUTHOR)) {
            statement.setLong(1, author.getId());
            statement.setLong(2, exhibit.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteExhibitAuthor(Author author, Exhibit exhibit) {
        String DELETE_EXHIBIT_AUTHOR = "DELETE FROM autor_exhibit WHERE autor_id = ? and exhibit_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_EXHIBIT_AUTHOR)) {
            statement.setLong(1, author.getId());
            statement.setLong(2, exhibit.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO method findByFullName
    @Override
    public Optional<Author> findByFullName(String fName, String lName) {
        return Optional.empty();
    }

    @Override
    public void save(Author objectToSave) {
        String SAVE_AUTHOR = "INSERT INTO autors(first_name, last_name) VALUES(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SAVE_AUTHOR)) {
            statement.setString(1, objectToSave.getFirstName());
            statement.setString(2, objectToSave.getLastName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String DELETE_AUTHOR = "DELETE FROM autors WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_AUTHOR)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        String FIND_AUTHOR_BY_ID = "SELECT * FROM autors WHERE id = ?";
        Optional<Author> author = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(FIND_AUTHOR_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    author = Optional.of(new AuthorRowMapper().mapRow(resultSet));
                }
            }

            return author;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> findAll() {
        String FIND_ALL_AUTHORS = "SELECT * FROM autors";
        AuthorRowMapper rowMapper = new AuthorRowMapper();
        List<Author> authors = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_AUTHORS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    authors.add(rowMapper.mapRow(resultSet));
                }
            }

            return authors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author newObject) {
        String UPDATE_AUTHOR = "UPDATE autors set first_name = ?, last_name = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHOR)) {
            statement.setString(1, newObject.getFirstName());
            statement.setString(2, newObject.getLastName());
            statement.setLong(3, newObject.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
