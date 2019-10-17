package dao.implemetsDao;

import dao.interfaceDao.BookInstanceDaoInterface;
import entities.Book;
import entities.BookInstance;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class BookInstanceDao implements BookInstanceDaoInterface {

    private final Connection connection;

    public BookInstanceDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(BookInstance bookInstance) {

    }

    @Override
    public void deleteById(Long id) {

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
        String query = "select books.title from book_instance left join books on "
                + "book_instance.id = books.id_book_instance where book_instance.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bookInstanceId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return Book.builder()
                    .title(resultSet.getString("title"))
                    .build();

        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
