package dao.implemetsDao;

import dao.interfaceDao.BookDaoInterface;
import entities.Book;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BookDao implements BookDaoInterface {

    private final Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(Book book) {
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
            log.error(e.getLocalizedMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractBooks(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException();
        }
    }

    private Stream<Book> extractBooks(ResultSet resultSet) throws SQLException {
        Stream.Builder<Book> bookBuilder = Stream.builder();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setAmountOfInstances(resultSet.getInt("amount_of_instances"));
            book.setTitle(resultSet.getString("title"));
            book.setDateOfRelease(resultSet.getDate("release_date").toLocalDate());
            book.setCategory(resultSet.getString("category"));
            bookBuilder.add(book);
        }
        resultSet.close();
        return bookBuilder.build();
    }

    @Override
    public void update(Long id, Book book) {
        //
    }

    @Override
    public void deleteById(Long id) {
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM book WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBooks(resultSet).findAny();
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException();
        }
    }

}
