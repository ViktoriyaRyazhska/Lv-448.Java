package service;


import dao.implemetsDao.AuthorDao;
import dao.implemetsDao.BookDao;
import dao.implemetsDao.BooksSubAuthors;
import entities.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {
    private AuthorDao authorDao;
    private BookDao bookDao;
    private BooksSubAuthors booksSubAuthors;

    public BookService(AuthorDao authorDao, BookDao bookDao, BooksSubAuthors booksSubAuthors) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.booksSubAuthors = booksSubAuthors;
    }

    public BookService() {
    }

    public void createBook(Book book) {
        bookDao.save(book);
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

    public List<Book> findAllBooksByAuthor(Long authorId) {
        return bookDao.findAllBooksByAuthorId(authorId);
    }

    public List<Book> findAllBooksBySubAuthor(Long subAuthorId) {
        return bookDao.findAllBooksBySubAuthor(subAuthorId);
    }

    public List<Book> findBooksBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDao.findBookBetweenDate(fromDate, toDate);
    }

    public Book findBookByTitle(String bookTitle) {
        return bookDao.findAllByTitle(bookTitle);
    }

    public Book getInfoByBookInstanceId(Long bookInstanceId) {
        return bookDao.getInfoByBookInstance(bookInstanceId);
    }


}
