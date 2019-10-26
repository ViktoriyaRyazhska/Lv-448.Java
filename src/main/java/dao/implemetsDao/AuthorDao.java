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

    /**
     * The connection field used for interaction with database.
     */
    private final Connection connection;

    /**
     * The authorDao field used for implementing Singleton.
     */
    private static AuthorDao authorDao;

    /**
     * Constructor, which creates an instance of the class using connection.
     *
     * @param connection used for interaction with database.
     */
    public AuthorDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method for getting an instance of AuthorDao class.
     *
     * @param connection used for interaction with database.
     * @return an instance of AuthorDao class.
     */
    public static AuthorDao getInstance(Connection connection) {
        if (authorDao == null) {
            authorDao = new AuthorDao(connection);
        }

        return authorDao;
    }

    /**
     * Method which saves objects in the database.
     *
     * @param author object which must be saved.
     */
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

    /**
     * Method used for updating objects in database.
     *
     * @param author object to update.
     */
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

    /**
     * Method used for finding an Author by his id.
     *
     * @param id author's id.
     * @return Author object wrapped in Optional.
     *
     * In case of absence an object with such id
     * method returns Optional.empty().
     */
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

    /**
     * Method used for finding all the sub authors by Book id
     *
     * @param bookId book's id.
     * @return List sub authors from the database.
     */
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

    /**
     * Method used for finding all authors from database.
     *
     * @return list of Author objects from database.
     */
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

    /**
     * Method used for finding all the authors
     * from the database.
     *
     * @param surname author's surname.
     * @return list of Author objects by its surname.
     */
    @Override
    public List<Author> findBySurname(String surname) {
        String query = "SELECT * FROM authors where last_name = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAuthors(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}