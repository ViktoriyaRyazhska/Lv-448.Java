package service;

import dao.implemetsDao.BookInstanceDao;
import entities.BookInstance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BookInstanceService {

    private BookInstanceDao bookInstanceDao;

    public BookInstanceService(BookInstanceDao bookInstanceDao) {
        this.bookInstanceDao = bookInstanceDao;
    }

    public void createBookInstance(BookInstance bookInstance) {
        bookInstanceDao.save(bookInstance);
    }

    public BookInstance findById(Long bookInstanceId) {
        return bookInstanceDao.findById(bookInstanceId).get();
    }

    public List<BookInstance> findAllBookInstanceByBookId(Long bookId) {
        return bookInstanceDao.findAllBookInstanceByBookId(bookId);
    }

    public boolean updateBookInstanced(BookInstance bookInstance) {
        if (bookInstanceDao.findById(bookInstance.getId()).isPresent()) {
            bookInstanceDao.update(bookInstance);
            return true;
        } else {
            return false;
        }
    }

    public boolean isAvailable(Long bookInstance) {
        return bookInstanceDao.isAvailable(bookInstance);
    }

    public Map<BookInstance, Long> findBookInstanceIdAndCountOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
        return bookInstanceDao.findBookInstanceIdAndCountOrderedByPeriod(firstDate, secondDate);
    }

    public List<BookInstance> findAllBookInstanceByTitle(String bookTitle) {
        return bookInstanceDao.findAllBookInstanceByTitle(bookTitle);
    }

    public List<BookInstance> findAllBookInstanceOnReadingByUser(Long userId) {
        return bookInstanceDao.findAllBookInstanceOnReading(userId);
    }

    public List<BookInstance> findAllReturnedBookInstanceByUser(Long userId) {
        return bookInstanceDao.findAllReturnedBookInstanceByUser(userId);
    }

    public Long getAmountOfTimesBookInstanceWasTaken(Long bookInstanceId) {
        return bookInstanceDao.getAmountOfTimesInstanceWasTaken(bookInstanceId);
    }
}
