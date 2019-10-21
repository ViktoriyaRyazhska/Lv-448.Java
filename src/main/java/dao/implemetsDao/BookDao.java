package dao.implemetsDao;

import dao.interfaceDao.BookDaoInterface;
import entities.Book;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookDao implements BookDaoInterface {

    private Connection connection;
    private AuthorDao authorDao;

    public BookDao(Connection connection, AuthorDao authorDao) {
        this.connection = connection;
        this.authorDao = authorDao;
    }

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
                            .author(authorDao.findById(resultSet.getLong("id_author")).get())
                            .build());

        }
        resultSet.close();
        return bookBuilder.build();
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBooks(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return extractBooks(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAllByAuthorSurname(String authorName) {
        String query = "SELECT * FROM books JOIN authors ON id_author = authors.id WHERE last_name = ?";
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, authorName);
            books = extractBooks(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

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

    // to do null pointer exeption
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

    @Override
    public Map<Book, Long> mostPopularBooks(LocalDate startPeriod, LocalDate endPeriod) {
        String query = "SELECT id_book,  COUNT(*) FROM orders " +
                "INNER JOIN book_instance bi ON orders.id_book_instance = bi.id " +
                "INNER JOIN books b ON bi.id_book = b.id " +
                "WHERE orders.date_order BETWEEN ? AND ?" +
                "GROUP BY b.id " +
                "ORDER BY COUNT(*) DESC;";
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

    @Override
    public Map<Book, Long> mostUnPopularBooks(LocalDate startPeriod, LocalDate endPeriod) {
        String query = "SELECT id_book,  COUNT(*) FROM orders " +
                "INNER JOIN book_instance bi ON orders.id_book_instance = bi.id " +
                "INNER JOIN books b ON bi.id_book = b.id " +
                "WHERE orders.date_order BETWEEN ? AND ?" +
                "GROUP BY b.id " +
                "ORDER BY COUNT(*) ASC;";
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

    //    Скільки разів брали певну книжку (в загальному)
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

    public Long getAverageTimeReadingBook(Long id) {
        String query = "select AVG(DATEDIFF(date_return,date_order)) from orders " +
                "inner join book_instance bi on orders.id_book_instance = bi.id " +
                "inner join books b on bi.id_book = b.id where id_book_instance = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong("AVG(DATEDIFF(date_return,date_order))");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


}