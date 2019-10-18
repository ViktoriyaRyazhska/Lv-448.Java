package dao.implemetsDao;

import dao.interfaceDao.AuthorDaoInterface;
import entities.Author;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Slf4j
public class AuthorDao implements AuthorDaoInterface {

    private final Connection connection;

    public AuthorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Author author) {
        String query = "INSERT INTO authors"
                + "(id, first_name, last_name) "
                + "VALUE (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, author.getId());
            preparedStatement.setString(2, author.getAuthorFirstName());
            preparedStatement.setString(3, author.getAuthorLastName());
            preparedStatement.executeUpdate();
            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                if (key.next()) {
                    author.setId(key.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        String query = "select * from authors where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAuthors(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(Long id, Author author) {
        String query = "UPDATE authors SET first_name = ?, last_name = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, author.getAuthorFirstName());
            preparedStatement.setString(2, author.getAuthorLastName());
            preparedStatement.setLong(3, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> findAll() {
        String query = "SELECT * FROM authors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAuthors(preparedStatement.getResultSet()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Author> extractAuthors(ResultSet resultSet) throws SQLException {
        Stream.Builder<Author> authors = Stream.builder();
        while (resultSet.next()) {
            authors.add(Author.builder()
                    .id(resultSet.getLong("id"))
                    .authorFirstName(resultSet.getString("first_name"))
                    .authorLastName(resultSet.getString("last_name"))
                    .build());
        }
        resultSet.close();
        return authors.build();
    }


    @Override
    public Optional<Author> findBySurname(String surname) {
        String query = "SELECT * FROM users where surname = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAuthors(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM authors where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}