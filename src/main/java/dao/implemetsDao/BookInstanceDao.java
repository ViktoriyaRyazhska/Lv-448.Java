package dao.implemetsDao;

import dao.interfaceDao.BookInstanceDaoInterface;
import entities.BookInstance;
import jdk.internal.jline.internal.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

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
            Log.error(e.getLocalizedMessage());
            throw new RuntimeException();
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
}
