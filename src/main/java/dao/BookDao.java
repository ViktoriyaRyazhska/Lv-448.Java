package dao;

import entities.Book;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao implements Crud<Book> {

    private final Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(Book book) throws SQLException {
        String query = "INSERT INTO books " +
                +"(id, amount_of_instances, title, release_date, category)" +
                "VALUE (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setLong(2, book.getAmountOfInstances());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setDate(4, Date.valueOf(book.getDateOfRelease()));
            preparedStatement.setString(5, book.getCategory());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractBooks(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private List<Book> extractBooks(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setAmountOfInstances(resultSet.getInt("amount_of_instances"));
            book.setTitle(resultSet.getString("title"));
            book.setDateOfRelease(resultSet.getDate("release_date").toLocalDate());
            book.setCategory(resultSet.getString("category"));
        }
        resultSet.close();
        return books;
    }

    @Override
    public void update(Long id, Book book) {

    }

    @Override
    public void delete(Book book) {
        String query ="SELECT * FROM books where title = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public int deleteById(Long id) {
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM book WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBoooks(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Optional<Book> extractBoooks(ResultSet resultSet) throws SQLException {
        Book book = null;
        while (resultSet.next()) {
            book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setAmountOfInstances(resultSet.getInt("amount_of_instances"));
            book.setTitle(resultSet.getString("title"));
            book.setDateOfRelease(resultSet.getDate("release_date").toLocalDate());
            book.setCategory(resultSet.getString("category"));
        }
        resultSet.close();
        return Optional.ofNullable(book);

    }

}
