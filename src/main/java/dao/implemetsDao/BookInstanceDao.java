package dao.implemetsDao;

import dao.interfaceDao.BookInstanceDaoInterface;
import entities.BookInstance;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookInstanceDao implements BookInstanceDaoInterface {

    private final Connection connection;

    public BookInstanceDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(BookInstance bookInstance) {
        String query = "INSERT INTO book_instance (is_available) VALUE (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBoolean(1, bookInstance.getIsAvailable());
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
        String query = "SELECT * FROM book_instance WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBookInstances(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookInstance> findAll() {
        String query = "SELECT * FROM book_instance";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractBookInstances(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
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
        String query = "UPDATE book_instance SET is_available = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, bookInstance.getIsAvailable());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isAvailable(Long id) {
        return findById(id).orElse(null).getIsAvailable();
    }


    public Map<Long, Long> findBookInstanceIdAndCountOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
        String query = "SELECT id_book_instance ,  COUNT(orders.date_order) FROM orders\n" +
                "    WHERE date_order BETWEEN ? AND ?\n" +
                "    GROUP BY id_book_instance;";
        Map<Long, Long> map = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(firstDate));
            preparedStatement.setDate(2, Date.valueOf(secondDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                map.put(resultSet.getLong("id_book_instance"), resultSet.getLong("COUNT(orders.date_order)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<BookInstance> findAllBookInstanceByTitle(String bookTitle) {
        String query = "SELECT book_instance.id FROM books JOIN book_instance ON books.id = book_instance.id_book WHERE title = ?;";
        List<BookInstance> bookInstanceId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookInstanceId.add(
                        findById(resultSet.getLong("id")).get());
            }
            return bookInstanceId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
