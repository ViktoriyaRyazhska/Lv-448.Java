package service;


import dao.implemetsDao.AuthorDao;
import dao.implemetsDao.BookDao;
import dao.implemetsDao.BookInstanceDao;
import entities.Book;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BookService {
    private AuthorDao authorDao;
    private BookDao bookDao;
    private BookInstanceDao bookInstanceDao;

    public BookService(AuthorDao authorDao, BookDao bookDao, BookInstanceDao bookInstanceDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.bookInstanceDao = bookInstanceDao;
    }

    public void createBook(Book book) {
        bookDao.save(book);
    }

    public void setSubAuthor(Long bookId, Long authorId) {
        bookDao.setSubAuthorForBook(bookId, authorId);
    }

    public void updateBook(Book book) {
        bookDao.update(book);
    }

    public List<Book> findAllBook() {
        return bookDao.findAll();
    }

    public Book findBookById(Long id) {
        return bookDao.findById(id).orElseThrow(() -> new IllegalArgumentException(""));
    }

    public List<Book> findAllBooksByAuthorSurname(String authorSurname) {
        return bookDao.findAllByAuthorSurname(authorSurname);
    }

    public List<Book> findAllBooksBySubAuthorId(Long subAuthorsId) {
        return bookDao.findAllBooksBySubAuthorId(subAuthorsId);
    }

    public List<Book> findAllBooksByAuthorId(Long authorId) {
        return bookDao.findAllBooksByAuthorId(authorId);
    }

    public List<Book> findBooksBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDao.findBookBetweenDate(fromDate, toDate);
    }

    public Book findBookByTitle(String bookTitle) {
        return bookDao.findBookByTitle(bookTitle);
    }

    public Book getInfoByBookInstanceId(Long bookInstanceId) {
        return bookDao.getInfoByBookInstance(bookInstanceId);
    }

    public Map<Book, Long> mostPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDao.mostPopularBooks(fromDate, toDate);
    }

    public Map<Book, Long> mostUnPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDao.mostUnPopularBooks(fromDate, toDate);
    }

    public Long getAmountOfTimesBookWasTaken(Long id) {
        return bookDao.getAmountOfTimesBookWasTaken(id);
    }

    public Integer[] averageTimeReadingBook(Long id) {
        return CalculateDateFromInt.calculateDaysFromInt(bookDao.getAverageTimeReadingBook(id));

    }
}
