package service;


import dao.implemetsDao.AuthorDao;
import dao.implemetsDao.BookDao;
import entities.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BookService {
    private AuthorDao authorDao;
    private BookDao bookDao;

    public BookService(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    public void createBook(Book book) {
        bookDao.save(book);
    }

    public void setSubAuthor(Long bookId, Long authorId){
        bookDao.setSubAuthorForBook(bookId, authorId);
    }

    public void updateBook(Book book) {
        bookDao.update(book);
    }

    public List<Book> findAllBook() {
        return bookDao.findAll();
    }

    public Book findBookById(Long id) {
        return bookDao.findById(id).get();
    }

    public List<Book> findAllBooksByAuthorSurname(String authorSurname) {
        return bookDao.findAllByAuthorSurname(authorSurname);
    }

    public List<Book> findAllBooksBySubAuthorId (Long subAuthorsId){
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

}
