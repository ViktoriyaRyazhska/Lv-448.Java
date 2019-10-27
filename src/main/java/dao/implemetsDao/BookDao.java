package dao.implemetsDao;

import dao.interfaceDao.AuthorDaoInterface;
import dao.interfaceDao.BookDaoInterface;
import entities.Book;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constants.QueryConstants.MAX_TOP_BOOKS;

public class BookDao implements BookDaoInterface {

    /**
     * The connection field used for interaction with database.
     */
    private final Connection connection;

    /**
     * This field used for loading and setting author for book.
     */
    private AuthorDaoInterface authorDaoInterface;

    /**
     * The bookDao field used for implementing Singleton.
     */
    private static BookDao bookDao;

    /**
     * Constructor, which creates an instance of the class using connection.
     *
     * @param connection used for interaction with database.
     */
    private BookDao(Connection connection, AuthorDaoInterface authorDaoInterface) {
        this.connection = connection;
        this.authorDaoInterface = authorDaoInterface;
    }

    /**
     * Method for getting instance of BookDao.
     *
     * @param connection Connection, that used for interaction with database.
     * @return instance of BookDao.
     */
    public static BookDao getInstance(Connection connection, AuthorDaoInterface authorDaoInterface) {
        if (bookDao == null) {
            bookDao = new BookDao(connection, authorDaoInterface);
        }
        return bookDao;
    }


    /**
     * Method for saving objects in database.
     *
     * @param book object, that must be saved.
     */
    public void save(Book book) {
        String query = "INSERT INTO books "
                + "(amount_of_instances, title, release_date, id_author)"
                + "VALUE (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, book.getAmountOfInstances());
            preparedStatement.setString(2, book.getTitle());
            if (book.getReleaseDate() != null) {
                preparedStatement.setDate(3, Date.valueOf(book.getReleaseDate()));
            } else {
                preparedStatement.setDate(3, null);
            }
            preparedStatement.setLong(4, book.getAuthor().getId());
            preparedStatement.executeUpdate();
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    book.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method sets the co-author for the book
     * by author id and book id in the intermediate table.
     *
     * @param bookId   id book for co-author.
     * @param authorId id co-author for book.
     */
    @Override
    public void setSubAuthorForBook(Long bookId, Long authorId) {
        String query = "INSERT INTO book_sub_authors (id_book, id_author) VALUE (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, authorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private Stream<Book> extractBooks(ResultSet resultSet) throws SQLException {
        Stream.Builder<Book> bookBuilder = Stream.builder();
        while (resultSet.next()) {
            bookBuilder.add(
                    Book.builder()
                            .id(resultSet.getLong("id"))
                            .amountOfInstances(resultSet.getInt("amount_of_instances"))
                            .title(resultSet.getString("title"))
                            .releaseDate(Optional.ofNullable(resultSet.getDate("release_date").toLocalDate()).orElse(null))
                            .author(authorDaoInterface.findById(resultSet.getLong("id_author")).get())
                            .build());

        }
        resultSet.close();
        return bookBuilder.build();
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
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBooks(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * Method, that returns all Books objects from database.
     *
     * @return list of all books from database.
     */
    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractBooks(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method, that returns all Book objects
     * by author surname from database.
     *
     * @param authorSurname author`s surname for filtration Book objects
     * @return list of books from database.
     */
    @Override
    public List<Book> findAllByAuthorSurname(String authorSurname) {
        String query = "SELECT * FROM books JOIN authors ON id_author = authors.id WHERE last_name = ?";
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, authorSurname);
            books = extractBooks(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }


    /**
     * Method, that returns all Book objects
     * by author id from database.
     *
     * @param authorId author`s id for filtration Book objects
     * @return list of books from database.
     */
    @Override
    public List<Book> findAllBooksByAuthorId(Long authorId) {
        String query = "SELECT * FROM books WHERE id_author = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBooks(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method, that returns all Book objects
     * by co-author id from database.
     *
     * @param authorId co-author`s id for filtration Book objects
     * @return list of books from database by co-author.
     */
    @Override
    public List<Book> findAllBooksBySubAuthorId(Long authorId) {
        String query = "SELECT id_book FROM book_sub_authors WHERE id_author = ?;";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(findById(resultSet.getLong("id_book")).orElse(null));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method, that returns all Book objects
     * where release date is between within a certain period.
     *
     * @param fromDate start date for filtration Book objects
     * @param toDate   end date for filtration Book objects
     * @return list of books from database.
     */
    @Override
    public List<Book> findBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        String query = "SELECT * FROM books WHERE release_date BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Optional.ofNullable((Date.valueOf(fromDate))).orElse(null));
            preparedStatement.setDate(2, Optional.ofNullable((Date.valueOf(toDate))).orElse(null));
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBooks(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Method, that returns all Book objects
     * by book`s title from database.
     *
     * @param title book`s title for filtration Book objects
     * @return list of books from database
     */
    @Override
    public Book findBookByTitle(String title) {
        String query = "SELECT * FROM books WHERE title = ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            return extractBooks(preparedStatement.executeQuery()).findAny().get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method, that returns Book object
     * by book instance id from database.
     *
     * @param bookInstanceId book instance`s id for filtration Book objects
     * @return list of books from database
     */
    @Override
    public Book getInfoByBookInstance(Long bookInstanceId) {
        String query = "SELECT * FROM books JOIN book_instance ON"
                + " book_instance.id_book = books.id WHERE book_instance.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bookInstanceId);
            return extractBooks(preparedStatement.executeQuery()).findAny().get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for updating Book object.
     *
     * @param book book you want to update
     */
    @Override
    public void update(Book book) {
        String query = "UPDATE books SET amount_of_instances = ?, title = ?, release_date = ?, id_author = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, book.getAmountOfInstances());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, Date.valueOf(book.getReleaseDate()));
            preparedStatement.setLong(4, book.getAuthor().getId());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method for deleting co-author from book by id.
     *
     * @param bookId   book`s id from witch we want delete co-author.
     * @param authorId id co-author what we want delete.
     */
    public void deleteSubAuthor(Long bookId, Long authorId) {
        String query = "DELETE FROM book_sub_authors WHERE id_book = ? AND id_author = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, authorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * Method, that returns a map of the most popular
     * Book objects and amount of their orders
     * by some period.
     *
     * @param startPeriod start date for filtration
     * @param endPeriod   end date for filtration
     * @return map of the most popular books from database
     */
    @Override
    public Map<Book, Long> mostPopularBooks(LocalDate startPeriod, LocalDate endPeriod) {
        String query = "SELECT id_book,  COUNT(*) FROM orders " +
                "INNER JOIN book_instance bi ON orders.id_book_instance = bi.id " +
                "INNER JOIN books b ON bi.id_book = b.id " +
                "WHERE orders.date_order BETWEEN ? AND ?" +
                "GROUP BY b.id " +
                "ORDER BY COUNT(*) DESC LIMIT " +
                MAX_TOP_BOOKS + ";";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(startPeriod));
            preparedStatement.setDate(2, Date.valueOf(endPeriod));
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Book, Long> bookCountMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                bookCountMap.put(
                        findById(resultSet.getLong("id_book")).get(),
                        resultSet.getLong("COUNT(*)")
                );
            }
            return bookCountMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method, that returns a map of the most unpopular
     * Book objects and amount of their orders
     * by some period.
     *
     * @param startPeriod start date for filtration
     * @param endPeriod   end date for filtration
     * @return map of the most unpopular books from database
     */
    @Override
    public Map<Book, Long> mostUnPopularBooks(LocalDate startPeriod, LocalDate endPeriod) {
        String query = "SELECT id_book,  COUNT(*) FROM orders " +
                "INNER JOIN book_instance bi ON orders.id_book_instance = bi.id " +
                "INNER JOIN books b ON bi.id_book = b.id " +
                "WHERE orders.date_order BETWEEN ? AND ?" +
                "GROUP BY b.id " +
                "ORDER BY COUNT(*) ASC LIMIT " +
                MAX_TOP_BOOKS + ";";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(startPeriod));
            preparedStatement.setDate(2, Date.valueOf(endPeriod));
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Book, Long> bookCountMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                bookCountMap.put(
                        findById(resultSet.getLong("id_book")).get(),
                        resultSet.getLong("COUNT(*)")
                );
            }
            return bookCountMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method, that returns amount of times the Book was ordered.
     *
     * @param id book`s id amount of orders we need to take
     * @return number of times the book was ordered
     */
    @Override
    public Long getAmountOfTimesBookWasTaken(Long id) {
        String query = "select count(date_order) from books inner join book_instance bi on books.id = bi.id_book inner join orders o on bi.id = o.id_book_instance where books.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong("count(date_order)");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    /** Method, that returns average time of reading a book.
     *
     * @param id book`s id
     * @return average amount of days the book was reading
     */
    @Override
    public Integer getAverageTimeReadingBook(Long id) {
        String query = "select AVG(DATEDIFF(date_return,date_order)) from orders " +
                "inner join book_instance bi on orders.id_book_instance = bi.id " +
                "inner join books b on bi.id_book = b.id where id_book = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("AVG(DATEDIFF(date_return,date_order))");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}