package dao;

import entities.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDao implements Crud<Author> {

    private final Connection connection;

    public AuthorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Author author) {
        String query = "INSERT INTO authors"
                + "(id, first_name, last_name) "
                + "VALUE (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, author.getId());
            preparedStatement.setString(2, author.getAuthorFirstName());
            preparedStatement.setString(3, author.getAuthorLastName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Author author) {}

    @Override
    public void delete(Author author){}

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        String query = "SELECT * FROM authors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractAuthors(preparedStatement.getResultSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Author> extractAuthors(ResultSet resultSet) throws SQLException {
        List<Author> authors = new ArrayList<>();
        while (resultSet.next()) {
            Author author = new Author();
            author.setId(resultSet.getLong("id"));
            author.setAuthorFirstName(resultSet.getString("authorFirstName"));
            author.setAuthorLastName(resultSet.getString("authorLastName"));
            authors.add(author);
        }
        resultSet.close();
        return authors;
    }
}