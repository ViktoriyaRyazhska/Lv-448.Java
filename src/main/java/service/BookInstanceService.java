package service;

import dao.implemetsDao.BookInstanceDao;
import entities.BookInstance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BookInstanceService {

    private BookInstanceDao bookInstanceDao;

    public void createBookInstance(BookInstance bookInstance) {
        bookInstanceDao.save(bookInstance);
    }

    public BookInstance findById(Long bookInstanceId) {
        return bookInstanceDao.findById(bookInstanceId).get();
    }

    public List<BookInstance> findAllBookInstanceByBookId(Long bookId) {
        return bookInstanceDao.findAllBookInstanceByBooID(bookId);
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

    public Map<Long, Long> findBookInstanceIdAndCountOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
        return findBookInstanceIdAndCountOrderedByPeriod(firstDate, secondDate);
    }

    public List<BookInstance> findAllBookInstanceByTitle(String bookTitle) {
        return findAllBookInstanceByTitle(bookTitle);
    }
}
