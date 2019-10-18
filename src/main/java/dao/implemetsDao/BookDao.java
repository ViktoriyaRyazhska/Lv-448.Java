package dao.implemetsDao;

import dao.interfaceDao.BookDaoInterface;
import entities.Book;
import entities.BookInstance;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookDao implements BookDaoInterface {

    private BookInstanceDao bookInstancedao;

    private final Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }


    public void save(Book book) {
        String query = "INSERT INTO books "
                + "(amount_of_instances, title, release_date)"
                + "VALUE (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, book.getAmountOfInstances());
            preparedStatement.setString(2, book.getTitle());
            if (book.getReleaseDate() != null) {
                preparedStatement.setDate(3, Date.valueOf(book.getReleaseDate()));
            } else {
                preparedStatement.setDate(3, null);
            }
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

    private Stream<Book> extractBooks(ResultSet resultSet) throws SQLException {
        Stream.Builder<Book> bookBuilder = Stream.builder();
        while (resultSet.next()) {
            bookBuilder.add(
                    Book.builder()
                            .id(resultSet.getLong("id"))
                            .amountOfInstances(resultSet.getInt("amount_of_instances"))
                            .title(resultSet.getString("title"))
                            .releaseDate(resultSet.getDate("release_date").toLocalDate())
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
        String query = "SELECT * FROM books join authors on id_author = authors.id where last_name = ?";
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, authorName);
            books = extractBooks(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    // to do delete duplicated code

    public List<Book> findAllBooksByAuthor(Long authorId) {
        String query = "select id_book from book_sub_authors where id_author = ?;";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(
                        findById(resultSet.getLong("id_book")).orElse(null)
                );
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAllBooksBySubAuthor(Long authorId) {
        String query = "SELECT id_book FROM book_sub_authors WHERE id_author = ?;";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(
                        findById(resultSet.getLong("id_book")).orElse(null));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Book> booksReleasedDuringIndependence(LocalDate fromDate, LocalDate toDate) {
        String query = "SELECT * FROM books where release_date between ? and ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Optional.ofNullable((Date.valueOf(fromDate))).orElse(null));
            preparedStatement.setDate(2, Optional.ofNullable((Date.valueOf(toDate))).orElse(null));
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractBooks(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<Book> findAllByTitle(String title) {
        String query = "SELECT * FROM books WHERE title = ?";
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            return extractBooks(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     @Override
    public List<Book> mostPopularBooks(LocalDate startPeriod, LocalDate endPeriod) {
        return null;
    }


    public void update(Long id, Book book) {
        //
    }


    //    @Override
    public Optional<Book> findById(long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return extractBooks(preparedStatement.executeQuery()).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<Book> findBookByBookInstanceId(Long instanceId) {
        String query = "SELECT * FROM books where id_book_instance=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, instanceId);
            return extractBooks(preparedStatement.executeQuery()).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException();
        }
    }


    public Map<Long, Long> findBookInstanceIdAndCountOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
        String firstQuery =
                "SELECT id_book_instance ,  COUNT(orders.date_order) FROM orders\n" +
                        "    WHERE date_order BETWEEN ? AND ?\n" +
                        "    GROUP BY id_book_instance;";

        Map<Long, Long> map = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(firstQuery)) {
            preparedStatement.setDate(1, Date.valueOf(firstDate));
            preparedStatement.setDate(2, Date.valueOf(secondDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                map.put(resultSet.getLong("id"), resultSet.getLong("COUNT(orders.date_order)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;

    }
}