package dao.implemetsDao;

import dao.interfaceDao.BookInstanceDaoInterface;
import entities.Book;
import entities.BookInstance;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class BookInstanceDao implements BookInstanceDaoInterface {

    private final Connection connection;

    public BookInstanceDao(Connection connection) {
        this.connection = connection;
    }


//    @Override
//    public void save(BookInstance bookInstance) {
//
//    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(BookInstance bookInstance) {
        String query = "INSERT INTO book_instance (is_available) VALUE (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBoolean(2, bookInstance.getIsAvailable());
            preparedStatement.executeUpdate();
            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                if (key.next()) {
                    bookInstance.setId(key.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BookInstance> findById(Long id) {
        String query = "SELECT * FROM book_instance where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return extractBookInstances(preparedStatement.executeQuery()).findAny();
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Stream<BookInstance> extractBookInstances(ResultSet resultSet) throws SQLException {
        Stream.Builder<BookInstance> builder = Stream.builder();
        while (resultSet.next()) {
            builder.add(
                    BookInstance.builder()
                            .id(resultSet.getLong("id"))
                            .isAvailable(resultSet.getBoolean("is_available"))
                            .build());
        }
        resultSet.close();
        return builder.build();
    }

    @Override
    public void update(Long id, BookInstance bookInstance) {

    }


    @Override
    public boolean isAvailable(Long id) {
        return findById(id).orElse(null).getIsAvailable();
    }

    @Override
    public Book getInfoByBookInstance(Long bookInstanceId) {
        String query = "select * from books where id_book_instance=1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Book book = new Book();
            while (resultSet.next()) {
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setReleaseDate(resultSet.getDate("release_date").toLocalDate());
                book.setAmountOfInstances(resultSet.getInt("amount_of_instances"));
            }
            return book;
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
