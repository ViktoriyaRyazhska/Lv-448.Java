package service;

import dao.implemetsDao.BookDao;
import dao.implemetsDao.BookInstanceDao;
import entities.Author;
import entities.Book;
import entities.BookInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private BookDao bookDao;
    private BookInstanceDao bookInstanceDao;


    public List<Book> findBookByAuthor(Author author) {
        return bookDao.findAllByAuthorName(author.getId());
    }

    public List<Book> getBookReleasedInIndependence(LocalDate fromDate, LocalDate toDate) {
        return bookDao.booksReleasedDuringIndependence(fromDate, toDate);
    }


    public List<Book> findAllBooksBySubAuthor(Long authorId) {
        return bookDao.findAllBooksBySubAuthor(authorId);
    }

    public List<BookInstance> findAllBookInstanceByBookId(Long bookId) {
        List<BookInstance> bookInstances = new ArrayList<>();
        List<Long> allBookInstanceIdByBookId = bookDao.findAllBookInstanceIdByBookId(bookId);
        while (allBookInstanceIdByBookId.iterator().hasNext()) {
            bookInstances.add(bookInstanceDao.findById(allBookInstanceIdByBookId.iterator().next()).orElse(null));
        }
        return bookInstances;
    }
}
