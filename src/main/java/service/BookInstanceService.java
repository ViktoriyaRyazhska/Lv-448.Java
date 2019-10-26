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

    /**
     * Default constructor
     */
    public BookInstanceService() {
        this.bookInstanceDao = DaoFactory.bookInstanceDao();
    }

    /**
     * Method for creating a user in database
     *
     * @param bookInstance that must be created
     */
    public void createBookInstance(BookInstance bookInstance) {
        bookInstanceDao.save(bookInstance);
    }

    /**
     * Method for finding a book instance by its id
     *
     * @param bookInstanceId book's instance id
     * @return Book Instance object
     */
    public BookInstance findById(Long bookInstanceId) {
        return bookInstanceDao.findById(bookInstanceId).get();
    }

    /**
     * Method, that convert book instance to bookInstanceDto
     * and returns all book instances
     * by book id.
     *
     * @param bookId book`s id for filtration Book instance objects
     * @return list of books from database.
     */
    public List<BookInstanceDto> findAllBookInstanceByBookId(Long bookId) {
        return bookInstanceDao.findAllBookInstanceByBookId(bookId)
                .stream()
                .map(this::convertEntityToBookInstanceDto)
                .collect(Collectors.toList());
    }

    /**
     * Method for updating book instances availability.
     *
     * @param bookInstanceId book`s instance id you want to update
     * @param available availability for setting to the book instance
     */
    public void setAvailableBookInstance(Long bookInstanceId, Boolean available) {
        bookInstanceDao.update(bookInstanceId, available);
    }

    /**
     * Method, that checks if a book instance is available.
     * @param bookInstance book`s instance id
     * @return availability of a book instance
     */
    public boolean isAvailable(Long bookInstance) {
        return bookInstanceDao.isAvailable(bookInstance);
    }


    /**
     * Method, that returns a map of book instances and amount of
     * their orders by some period.
     *
     * @param firstDate start date for filtration
     * @param secondDate end date for filtration
     * @return map of the most popular books from database
     */
    public Map<BookInstance, Long> findBookInstanceIdAndCountOrderedByPeriod(LocalDate firstDate, LocalDate secondDate) {
        return bookInstanceDao.amountBookInstanceWasOrderedByPeriod(firstDate, secondDate);
    }


    /**
     * Method, that returns all book instances
     * by book`s title.
     *
     * @param bookTitle book`s title for filtration Book objects
     * @return list of books instances
     */
    public List<BookInstance> findAllBookInstanceByTitle(String bookTitle) {
        return bookInstanceDao.findAllBookInstanceByTitle(bookTitle);
    }

    /**
     * Method, that returns all book instances which are now reading
     * by user.
     *
     * @param userId user`s id for filtration
     * @return list of books instance
     */
    public List<BookInstance> findAllBookInstanceOnReadingByUser(Long userId) {
        return bookInstanceDao.findAllBookInstanceOnReading(userId);
    }

    /**
     * Method, that returns all book instances which are returned
     * by user.
     *
     * @param userId user`s id for filtration
     * @return list of books instance
     */
    public List<BookInstance> findAllReturnedBookInstanceByUser(Long userId) {
        return bookInstanceDao.findAllReturnedBookInstanceByUser(userId);
    }

    /**
     * Method, that returns amount of times the book
     * instance was taken.
     *
     * @param bookInstanceId book`s instance id for filtration
     * @return number amount was taken book instance
     */
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
