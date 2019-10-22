package service;


import dao.interfaceDao.AuthorDaoInterface;
import dao.interfaceDao.BookDaoInterface;
import dao.interfaceDao.BookInstanceDaoInterface;
import database.DaoFactory;
import entities.Author;
import entities.Book;
import entities.BookInstance;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BookService {
    private AuthorDaoInterface authorDaoInterface;
    private BookDaoInterface bookDaoInterface;
    private BookInstanceDaoInterface bookInstanceDaoInterface;

    public BookService() {
        this.authorDaoInterface = DaoFactory.authorDao();
        this.bookDaoInterface = DaoFactory.bookDao();
        this.bookInstanceDaoInterface = DaoFactory.bookInstanceDao();
    }

    public void createBook(Book book, Author author) {
        if (author != null) {
            book.setAuthor(author);
            authorDaoInterface.save(author);
            bookDaoInterface.save(book);
            for (int i = 0; i < book.getAmountOfInstances(); i++) {
                BookInstance bookInstance = BookInstance.builder()
                        .isAvailable(true)
                        .book(findBookById(book.getId())).build();
                bookInstanceDaoInterface.save(bookInstance);
            }
        } else {
            bookDaoInterface.save(book);
            for (int i = 0; i < book.getAmountOfInstances(); i++) {
                BookInstance bookInstance = BookInstance.builder()
                        .isAvailable(true)
                        .book(findBookById(book.getId())).build();
                bookInstanceDaoInterface.save(bookInstance);
            }
        }
    }

    public void setSubAuthor(Long bookId, Long authorId) {
        bookDaoInterface.setSubAuthorForBook(bookId, authorId);
    }

    public void updateBook(Book book) {
        bookDaoInterface.update(book);
    }

    public List<Book> findAllBook() {
        return bookDaoInterface.findAll();
    }

    public Book findBookById(Long id) {
        return bookDaoInterface.findById(id).get();
    }

    public List<Book> findAllBooksByAuthorSurname(String authorSurname) {
        return bookDaoInterface.findAllByAuthorSurname(authorSurname);
    }

    public List<Book> findAllBooksBySubAuthorId(Long subAuthorsId) {
        return bookDaoInterface.findAllBooksBySubAuthorId(subAuthorsId);
    }

    public List<Book> findAllBooksByAuthorId(Long authorId) {
        return bookDaoInterface.findAllBooksByAuthorId(authorId);
    }

    public List<Book> findBooksBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.findBookBetweenDate(fromDate, toDate);
    }

    public Book findBookByTitle(String bookTitle) {
        return bookDaoInterface.findBookByTitle(bookTitle);
    }

    public Book getInfoByBookInstanceId(Long bookInstanceId) {
        return bookDaoInterface.getInfoByBookInstance(bookInstanceId);
    }

    public Map<Book, Long> mostPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.mostPopularBooks(fromDate, toDate);
    }

    public Map<Book, Long> mostUnPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.mostUnPopularBooks(fromDate, toDate);
    }

    public Long getAmountOfTimesBookWasTaken(Long id) {
        return bookDaoInterface.getAmountOfTimesBookWasTaken(id);
    }

    public Integer[] averageTimeReadingBook(Long id) {
        return CalculateDateFromInt.calculateDaysFromInt(bookDaoInterface.getAverageTimeReadingBook(id));

    }
}
