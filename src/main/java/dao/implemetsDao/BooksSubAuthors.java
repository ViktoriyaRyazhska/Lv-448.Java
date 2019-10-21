package dao.implemetsDao;

import entities.Author;
import entities.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BooksSubAuthors {
    private Connection connection;
    private AuthorDao authorDao;
    private BookDao bookDao;

    public BooksSubAuthors(Connection connection, AuthorDao authorDao, BookDao bookDao) {
        this.connection = connection;
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }


    public List<Author> findAllSubAuthorByBookId(Long bookId) {
        String query = "SELECT id_author FROM book_sub_authors where id_book = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> subAuthors = new ArrayList<>();
            while (resultSet.next()) {
                long id_author = resultSet.getLong("id_author");
                Optional<Author> byId = authorDao.findById(id_author);
                subAuthors.add(byId.get());

            }
            return subAuthors;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public List<Book> findAllBooksBySubAuthorId(Long authorId) {
        String query = "SELECT id_book FROM book_sub_authors where id_author = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> idBooksBySubAuthor = new ArrayList<>();
            while (resultSet.next()) {
                idBooksBySubAuthor.add(bookDao.findById(resultSet.getLong("id_book")).get());
            }
            return idBooksBySubAuthor;
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
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
}
