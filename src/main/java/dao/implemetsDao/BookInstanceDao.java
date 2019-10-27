package dao.implemetsDao;

import dao.interfaceDao.BookDaoInterface;
import dao.interfaceDao.BookInstanceDaoInterface;
import entities.BookInstance;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookInstanceDao implements BookInstanceDaoInterface {


    /**
     * The connection field used for interaction with database.
     */
    private final Connection connection;
    /**
     * This field used for loading and setting book for book instance
     */
    private BookDaoInterface bookDaoInterface;

    /**
     * The bookInstanceDao field used for implementing Singleton.
     */
    private static BookInstanceDao bookInstanceDao;

    /**
     * Constructor, which creates an instance of the class using connection.
     *
     * @param connection used for interaction with database.
     */
    private BookInstanceDao(Connection connection, BookDaoInterface bookDaoInterface) {
        this.connection = connection;
        this.bookDaoInterface = bookDaoInterface;
    }

    /**
     * Method for getting instance of BookInstanceDao.
     *
     * @param connection Connection, that used for interaction with database.
     * @return instance of BookInstanceDao.
     */
    public static BookInstanceDao getInstance(Connection connection, BookDaoInterface bookDaoInterface) {
        if (bookInstanceDao == null) {
            bookInstanceDao = new BookInstanceDao(connection, bookDaoInterface);
        }
        return bookInstanceDao;
    }


    /**
     * Method for saving objects in database.
     *
     * @param bookInstance object, that must be saved.
     */
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


    /**
     * Method, that returns object wrapped in Optional by id.
     *
     * @param id object's id.
     * @return object wrapped in Optional that has id field value
     * the same as id parameter value.
     * If there is no object with such id value
     * it returns Optional.empty
     */
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

    /**
     * Method, that returns all Book instances
     * by book id from database.
     *
     * @param bookId book`s id for filtration Book instance objects
     * @return list of books from database.
     */
    @Override
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
                            .book(bookDaoInterface.findById(resultSet.getLong("id_book")).get())
                            .build());
        }
        resultSet.close();
        return builder.build();
    }


    /**
     * Method for updating Book instance object.
     *
     * @param bookInstanceId book`s instance id you want to update
     * @param available availability for setting to the book instance
     */
    @Override
    public void update(Long bookInstanceId, Boolean available) {
        String query = "UPDATE book_instance SET is_available = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, available);
            preparedStatement.setLong(2, bookInstanceId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method, that checks if a book instance is available.
     * @param id book`s instance id
     * @return availability of a book instance
     */
    @Override
    public boolean isAvailable(Long id) {
        return findById(id).orElse(null).getIsAvailable();
    }

    /**
     * Method, that returns a map of book instances and amount of
     * their orders by some period.
     *
     * @param firstDate start date for filtration
     * @param secondDate end date for filtration
     * @return map of the most popular books from database
     */
    @Override
    public Map<BookInstance, Long> amountBookInstanceWasOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
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

    /**
     * Method, that returns all book instances
     * by book`s title from database.
     *
     * @param bookTitle book`s title for filtration book instances
     * @return list of books instances
     */
    @Override
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

    /**
     * Method, that returns all book instances which are now reading
     * by user from database.
     *
     * @param userId user`s id for filtration
     * @return list of books instance from database
     */
    @Override
    public List<BookInstance> findAllBookInstanceOnReading(Long userId) {
        String query = "SELECT id_book_instance FROM users LEFT JOIN orders ON"
                + " users.id = orders.id_users WHERE date_return is null AND id_users = ?";
        return findAllBookInstanceByUser(userId, query);
    }


    /**
     * Method, that returns all book instances which are returned
     * by user from database.
     *
     * @param userId user`s id for filtration
     * @return list of books instance from database
     */
    @Override
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


    /**
     * Method, that returns amount of times the book
     * instance was taken from database.
     *
     * @param id book`s instance id for filtration
     * @return amount of times book instance was taken
     */
    @Override
    public Long getAmountOfTimesInstanceWasTaken(Long id) {
        String query = "select count(date_order) from orders where id_book_instance = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong("count(date_order)");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
