package service;


import dto.BookDto;
import dao.interfaceDao.AuthorDaoInterface;
import dao.interfaceDao.BookDaoInterface;
import database.DaoFactory;
import dto.BookInstanceDto;
import entities.Author;
import entities.Book;
import entities.BookInstance;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookService {
    private AuthorDaoInterface authorDaoInterface;
    private BookDaoInterface bookDaoInterface;
    private BookInstanceService bookInstanceService;

    public BookService() {
        this.authorDaoInterface = DaoFactory.authorDao();
        this.bookDaoInterface = DaoFactory.bookDao();
        this.bookInstanceService = new BookInstanceService();
    }

    public void createBook(Book book, Author author) {
        if (author != null) {
            book.setAuthor(author);
            authorDaoInterface.save(author);
            bookDaoInterface.save(book);
            for (int i = 0; i < book.getAmountOfInstances(); i++) {
                BookInstance bookInstance = BookInstance.builder()
                        .isAvailable(true)
                        .book(findBookById(book.getId())).build();
                bookInstanceService.createBookInstance(bookInstance);
            }
        } else {
            bookDaoInterface.save(book);
            for (int i = 0; i < book.getAmountOfInstances(); i++) {
                BookInstance bookInstance = BookInstance.builder()
                        .isAvailable(true)
                        .book(findBookById(book.getId())).build();
                bookInstanceService.createBookInstance(bookInstance);
            }
        }
    }

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

    public void setSubAuthor(Long bookId, Long authorId) {
        bookDaoInterface.setSubAuthorForBook(bookId, authorId);
    }

    public void updateBook(Book book) {
        bookDaoInterface.update(book);
    }

    public List<BookDto> findAllBook() {
        return bookDaoInterface.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public Book findBookById(Long id) {
        return bookDaoInterface.findById(id).get();
    }

    public List<Book> findAllBooksByAuthorSurname(String authorSurname) {
        return bookDaoInterface.findAllByAuthorSurname(authorSurname);
    }

    public List<BookDto> findAllBooksBySubAuthorId(Long subAuthorsId) {
        List<BookDto> collect = bookDaoInterface.findAllBooksBySubAuthorId(subAuthorsId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        collect.stream().forEach(System.out::println);

        return collect;
    }

    public List<BookDto> findAllBooksByAuthorId(Long authorId) {
        return bookDaoInterface.findAllBooksByAuthorId(authorId).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public List<BookDto> findBooksBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.findBookBetweenDate(fromDate, toDate)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> findBookByTitle(String bookTitle) {
        List<BookDto> listBook = new ArrayList<>();
        if (bookDaoInterface.findBookByTitle(bookTitle) == null) {
            throw new IllegalArgumentException("Book is not found!");
        } else {
            listBook.add(convertEntityToDto(bookDaoInterface.findBookByTitle(bookTitle)));
            return listBook;
        }
    }

    public Book getInfoByBookInstanceId(Long bookInstanceId) {
        return bookDaoInterface.getInfoByBookInstance(bookInstanceId);
    }

    public Map<BookDto, Long> mostPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return mapToBookDto(bookDaoInterface.mostPopularBooks(fromDate, toDate));
    }

    private Map<BookDto, Long> mapToBookDto(Map<Book, Long> map) {
        Map<BookDto, Long> resultMap = new HashMap<>();
        for (Map.Entry<Book, Long> entry : map.entrySet()) {
            resultMap.put(convertEntityToDto(entry.getKey()), entry.getValue());
        }
        return resultMap;
    }

    public Map<BookDto, Long> mostUnPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return mapToBookDto(bookDaoInterface.mostUnPopularBooks(fromDate, toDate));
    }

    public Long getAmountOfTimesBookWasTaken(Long id) {
        return bookDaoInterface.getAmountOfTimesBookWasTaken(id);
    }

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
                .build();
        return bookDto;
    }
}
