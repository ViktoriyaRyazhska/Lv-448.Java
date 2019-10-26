package service;

import dao.interfaceDao.BookInstanceDaoInterface;
import database.DaoFactory;
import dto.BookInstanceDto;
import entities.BookInstance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookInstanceService {

    private BookInstanceDaoInterface bookInstanceDao;

    public BookInstanceService() {
        this.bookInstanceDao = DaoFactory.bookInstanceDao();
    }

    public void createBookInstance(BookInstance bookInstance) {
        bookInstanceDao.save(bookInstance);
    }

    public BookInstance findById(Long bookInstanceId) {
        return bookInstanceDao.findById(bookInstanceId).get();
    }

    public List<BookInstanceDto> findAllBookInstanceByBookId(Long bookId) {
        return bookInstanceDao.findAllBookInstanceByBookId(bookId)
                .stream()
                .map(this::convertEntityToBookInstanceDto)
                .collect(Collectors.toList());
    }

    public void setAvailableBookInstance(Long bookInstanceId, Boolean available) {
        bookInstanceDao.update(bookInstanceId, available);
    }

    public boolean isAvailable(Long bookInstance) {
        return bookInstanceDao.isAvailable(bookInstance);
    }

    public Map<BookInstance, Long> findBookInstanceIdAndCountOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
        return bookInstanceDao.amountBookInstanceWasOrderedByPeriod(firstDate, secondDate);
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

    private BookInstanceDto convertEntityToBookInstanceDto(BookInstance bookInstance) {
        return BookInstanceDto.builder()
                .id(bookInstance.getId())
                .isAvailable(bookInstance.getIsAvailable())
                .book(bookInstance.getBook())
                .countWasTaken(getAmountOfTimesBookInstanceWasTaken(bookInstance.getId()))
                .build();
    }
}
