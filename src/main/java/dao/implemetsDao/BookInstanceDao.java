package dao.implemetsDao;

import dao.interfaceDao.BookInstanceDaoInterface;
import entities.BookInstance;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class    BookInstanceDao implements BookInstanceDaoInterface {

    private final Connection connection;
    private BookDao bookDao;

    public BookInstanceDao(Connection connection, BookDao bookDao) {
        this.connection = connection;
        this.bookDao = bookDao;
    }

    @Override
    public void save(BookInstance bookInstance) {
        String query = "INSERT INTO book_instance (is_available, id_book) VALUE (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBoolean(1, bookInstance.getIsAvailable());
            preparedStatement.setLong(2, bookInstance.getBook().getId());
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

    public List<BookInstance> findAllBookInstanceByBookId(Long bookId) {
        String query = "SELECT * FROM book_instance WHERE id_book = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bookId);
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
                            .book(bookDao.findById(resultSet.getLong("id_book")).get())
                            .build());
        }
        resultSet.close();
        return builder.build();
    }

    @Override
    public void update(BookInstance bookInstance) {
        String query = "UPDATE book_instance SET is_available = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, bookInstance.getIsAvailable());
            preparedStatement.setLong(2, bookInstance.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isAvailable(Long id) {
        return findById(id).orElse(null).getIsAvailable();
    }


    public Map<BookInstance, Long> findBookInstanceIdAndCountOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
        String query = "SELECT id_book_instance ,  COUNT(orders.date_order) FROM orders\n" +
                "    WHERE date_order BETWEEN ? AND ?\n" +
                "    GROUP BY id_book_instance;";
        Map<BookInstance, Long> map = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(firstDate));
            preparedStatement.setDate(2, Date.valueOf(secondDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                map.put(findById(resultSet.getLong("id_book_instance")).get(),
                        resultSet.getLong("COUNT(orders.date_order)"));
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

    public List<BookInstance> findAllBookInstanceOnReading(Long userId) {
        String query = "SELECT id_book_instance FROM users LEFT JOIN orders ON"
                + " users.id = orders.id_users WHERE date_return is null AND id_users = ?";
        return findAllBookInstanceByUser(userId, query);
    }

    public List<BookInstance> findAllReturnedBookInstanceByUser(Long userId) {
        String query = "SELECT id_book_instance FROM users LEFT JOIN orders ON"
                + " users.id = orders.id_users WHERE date_return is not null AND id_users = ?";
        return findAllBookInstanceByUser(userId, query);
    }

    private List<BookInstance> findAllBookInstanceByUser(Long userId, String query) {
        List<BookInstance> bookInstances = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookInstances.add(findById(resultSet.getLong("id_book_instance")).get());
            }
            return bookInstances;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    Скільки разів брали певну книжку по примірникам
    public Long getAmountOfTimesInstanceWasTaken(Long id) {
        String query = "select count(date_order) from orders where id_book_instance = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong("count(date_order)");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
