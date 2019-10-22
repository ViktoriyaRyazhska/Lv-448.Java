package dao.implemetsDao;

import dao.interfaceDao.AuthorDaoInterface;
import entities.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthorDao implements AuthorDaoInterface {

    private Connection connection;
    private static AuthorDao authorDao;

    public AuthorDao(Connection connection) {
        this.connection = connection;
    }

    public static AuthorDao getInstance(Connection connection) {
        if (authorDao == null) {
            authorDao = new AuthorDao(connection);
        }

        return authorDao;
    }

    @Override
    public void save(Author author) {
        String query = "INSERT INTO authors"
                + "(first_name, last_name) "
                + "VALUE (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getAuthorFirstName());
            preparedStatement.setString(2, author.getAuthorLastName());
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
    public void update(Author author) {
        String query = "UPDATE authors SET first_name = ?, last_name = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, author.getAuthorFirstName());
            preparedStatement.setString(2, author.getAuthorLastName());
            preparedStatement.setLong(3, author.getId());
            System.out.println("updated");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        String query = "SELECT * FROM authors where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAuthors(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> findAllSubAuthorByBookId(Long bookId) {
        String query = "SELECT id_author FROM book_sub_authors where id_book = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> subAuthors = new ArrayList<>();
            while (resultSet.next()) {
                long id_author = resultSet.getLong("id_author");
                subAuthors.add(findById(id_author).get());
            }
            return subAuthors;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Author> findAll() {
        String query = "SELECT * FROM authors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAuthors(preparedStatement.executeQuery()).collect(Collectors.toList());
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
        String query = "SELECT * FROM authors where last_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAuthors(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}