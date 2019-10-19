package service;


import dao.implemetsDao.AuthorDao;
import dao.implemetsDao.BookDao;
import entities.Book;
import entities.BookInstance;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class BookService {
    private AuthorDao authorDao;
    private BookDao bookDao;

    public void createBook(Book book) {
        bookDao.save(book);
    }

    public void updateBook(Long id, Book book) {
        bookDao.update(id, book);
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

    public List<Book> findAllBooksByAuthor(Long authorId) {
        return bookDao.findAllBooksByAuthorId(authorId);
    }

    public List<Book> findAllBooksBySubAuthor(Long subAuthorId) {
        return bookDao.findAllBooksBySubAuthor(subAuthorId);
    }

    public List<Book> findBooksBetweenDate(LocalDate fromDate, LocalDate toDate){
        return bookDao.findBookBetweenDate(fromDate, toDate);
    }

    public Book findBookByTitle(String bookTitle){
        return bookDao.findAllByTitle(bookTitle);
    }

    public Book getInfoByBookInstanceId(Long bookInstanceId){
        return bookDao.getInfoByBookInstance(bookInstanceId);
    }

}
