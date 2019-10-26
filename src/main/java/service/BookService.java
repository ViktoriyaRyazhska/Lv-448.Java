package service;


import dao.interfaceDao.BookDaoInterface;
import database.DaoFactory;
import dto.BookDto;
import dto.BookInstanceDto;
import entities.Book;
import entities.BookInstance;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookService {

    private AuthorService authorService;
    private BookDaoInterface bookDaoInterface;
    private BookInstanceService bookInstanceService;
    private UserService userService;

    /**
     * Default constructor
     */
    public BookService() {
        this.authorService = new AuthorService();
        this.bookDaoInterface = DaoFactory.bookDao();
        this.bookInstanceService = new BookInstanceService();
        this.userService = new UserService();
    }

    /**
     * Method, that creates  book
     *
     * @param book book it was create
     */
    public void createBook(Book book) {
           bookDaoInterface.save(book);
            for (int i = 0; i < book.getAmountOfInstances(); i++) {
                BookInstance bookInstance = BookInstance.builder()
                        .isAvailable(true)
                        .book(findBookById(book.getId())).build();
                bookInstanceService.createBookInstance(bookInstance);
            }
        }

    /**
     * Method, that check availability for book
     *
     * @param book book for which need check availability
     * @return yes if the book is available or no if its is not available
     */
    public String isAnyAvailable(Book book) {
        List<BookInstanceDto> allBookInstanceByBookId = bookInstanceService.findAllBookInstanceByBookId(book.getId());
        int count = 0;
        for (BookInstanceDto bookInstanceDto : allBookInstanceByBookId) {
            if (bookInstanceDto.getIsAvailable()) {
                count++;
            }
        }
        return count > 0 ? "Yes" : "No";
    }

    /**
     * This method sets the co-author for the book
     * by author id and book id in the intermediate table.
     *
     * @param bookId   id book for co-author.
     * @param authorId id co-author for book.
     */
    public void setSubAuthor(Long bookId, Long authorId) {
        bookDaoInterface.setSubAuthorForBook(bookId, authorId);
    }

    /**
     * Method for updating Book object.
     *
     * @param book book you want to update
     */
    public void updateBook(Book book) {
        bookDaoInterface.update(book);
    }

    /**
     * Method, that returns all Books objects from database
     * and convert their to bookDto.
     *
     * @return list of bookDto
     */
    public List<BookDto> findAllBook() {
        return bookDaoInterface.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    /**
     * Method, that returns book
     *
     * @param id book`s id
     * @return Book object
     */
    public Book findBookById(Long id) {
        return bookDaoInterface.findById(id).get();
    }

    /**
     * Method, that convert book to bookDto and returns all books
     * by author surname.
     *
     * @param authorSurname author`s surname for filtration Book objects
     * @return list of bookDto
     */
    public List<BookDto> findAllBooksByAuthorSurname(String authorSurname) {
        return bookDaoInterface.findAllByAuthorSurname(authorSurname)
                .stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    /**
     * Method, that convert book to bookDto and returns all books
     * by co-author.
     *
     * @param subAuthorsId author`s id for filtration
     * @return list of bookDto
     */
    public List<BookDto> findAllBooksBySubAuthorId(Long subAuthorsId) {
        List<BookDto> collect = bookDaoInterface.findAllBooksBySubAuthorId(subAuthorsId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        return collect;
    }


    /**
     * Method, that convert book to bookDto and returns all books
     * by author.
     *
     * @param authorId author`s id for filtration
     * @return list of bookDto
     */
    public List<BookDto> findAllBooksByAuthorId(Long authorId) {
        return bookDaoInterface.findAllBooksByAuthorId(authorId).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    /**
     * Method, that convert book to bookDto
     * and returns all books where release date
     * come under within certain period.
     *
     * @param fromDate start date for filtration
     * @param toDate   end date for filtration
     * @return list of bookDto
     */
    public List<BookDto> findBooksBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.findBookBetweenDate(fromDate, toDate)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    /**Method, that check if exist some book with some title,
     * and if it`s exist convert it book to
     * bookDto and returns all books by book`s title
     * if it`s not exist throws exception with message.
     *
     * @param bookTitle book`s title for filtration Book objects
     * @return list of bookDto
     */
    public List<BookDto> findBookByTitle(String bookTitle) {
        List<BookDto> listBook = new ArrayList<>();
        if (bookDaoInterface.findBookByTitle(bookTitle) == null) {
            throw new IllegalArgumentException("Book is not found!");
        } else {
            listBook.add(convertEntityToDto(bookDaoInterface.findBookByTitle(bookTitle)));
            return listBook;
        }
    }

    /**
     * Method, that returns Book object
     * by book instance id from database.
     *
     * @param bookInstanceId book instance`s id for filtration Book objects
     * @return list of books from database
     */
    public Book getInfoByBookInstanceId(Long bookInstanceId) {
        return bookDaoInterface.getInfoByBookInstance(bookInstanceId);
    }

    /**
     * Method, that convert book to book Dto
     * and returns a map of the most popular
     * books and amount of their orders
     * by some period.
     *
     * @param fromDate start date for filtration
     * @param toDate   end date for filtration
     * @return map of the most popular books
     */
    public Map<BookDto, Long> mostPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return mapToBookDto(bookDaoInterface.mostPopularBooks(fromDate, toDate));
    }

    private Map<BookDto, Long> mapToBookDto(Map<Book, Long> map) {
        Map<BookDto, Long> resultMap = new LinkedHashMap<>();
        for (Map.Entry<Book, Long> entry : map.entrySet()) {
            resultMap.put(convertEntityToDto(entry.getKey()), entry.getValue());
        }
        return resultMap;
    }


    /**
     * Method, that convert book to book Dto
     * and returns a map of the most unpopular
     * books and amount of their orders
     * by some period.
     *
     * @param fromDate start date for filtration
     * @param toDate   end date for filtration
     * @return map of the most unpopular books
     */
    public Map<BookDto, Long> mostUnPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return mapToBookDto(bookDaoInterface.mostUnPopularBooks(fromDate, toDate));
    }

    /**
     * Method, that returns amount of times the Book was ordered.
     *
     * @param id book`s id amount of orders we need to take
     * @return number of times the book was ordered
     */
    public Long getAmountOfTimesBookWasTaken(Long id) {
        return bookDaoInterface.getAmountOfTimesBookWasTaken(id);
    }

    /** Method, that returns average time the Book was reading.
     *
     * @param id book`s id amount of orders we need to take
     * @return number average number of days the book was reading
     */
    public String averageTimeReadingBook(Long id) {
        Integer[] integers = CalculateDateFromInt.calculatePeriodFromDays(bookDaoInterface.getAverageTimeReadingBook(id));
        return "Years: " + integers[0] + " Months: " + integers[1] + " Days: " + integers[2];
    }

    private BookDto convertEntityToDto(Book book) {
        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .amountOfInstances(book.getAmountOfInstances())
                .releaseDate(book.getReleaseDate())
                .isAvailable(isAnyAvailable(book))
                .author(book.getAuthor())
                .averageTimeReading(averageTimeReadingBook(book.getId()))
                .amountOfTimesBookWasTaken(getAmountOfTimesBookWasTaken(book.getId()))
                .bookInstance(bookInstanceService.findAllBookInstanceByBookId(book.getId()))
                .averageAgeUsers(userService.averageAgeUsersByBook(book.getId()))
                .subAuthors(authorService.findAllSubAuthorByBookId(book.getId()))
                .build();
        return bookDto;
    }
}
