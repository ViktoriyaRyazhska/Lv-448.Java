package service;


import dto.BookDto;
import dao.interfaceDao.AuthorDaoInterface;
import dao.interfaceDao.BookDaoInterface;
import dao.interfaceDao.BookInstanceDaoInterface;
import database.DaoFactory;
import entities.Author;
import entities.Book;
import entities.BookInstance;
import utils.CalculateDateFromInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookService {
    private AuthorDaoInterface authorDaoInterface;
    private BookDaoInterface bookDaoInterface;
    private BookInstanceDaoInterface bookInstanceDaoInterface;

    public BookService() {
        this.authorDaoInterface = DaoFactory.authorDao();
        this.bookDaoInterface = DaoFactory.bookDao();
        this.bookInstanceDaoInterface = DaoFactory.bookInstanceDao();
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
                bookInstanceDaoInterface.save(bookInstance);
            }
        } else {
            bookDaoInterface.save(book);
            for (int i = 0; i < book.getAmountOfInstances(); i++) {
                BookInstance bookInstance = BookInstance.builder()
                        .isAvailable(true)
                        .book(findBookById(book.getId())).build();
                bookInstanceDaoInterface.save(bookInstance);
            }
        }
    }

    public String isAnyAvailable(Book book) {
        List<BookInstance> allBookInstanceByBookId = bookInstanceDaoInterface.findAllBookInstanceByBookId(book.getId());
        int count = 0;
        for (BookInstance bookInstance : allBookInstanceByBookId) {
            if (bookInstance.getIsAvailable()) {
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

    public List<Book> findBooksBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.findBookBetweenDate(fromDate, toDate);
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

    public Map<Book, Long> mostPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.mostPopularBooks(fromDate, toDate);
    }

    public Map<Book, Long> mostUnPopularBookBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return bookDaoInterface.mostUnPopularBooks(fromDate, toDate);
    }

    public Long getAmountOfTimesBookWasTaken(Long id) {
        return bookDaoInterface.getAmountOfTimesBookWasTaken(id);
    }

    public Integer[] averageTimeReadingBook(Long id) {
        return CalculateDateFromInt.calculateDaysFromInt(bookDaoInterface.getAverageTimeReadingBook(id));

    }

    private BookDto convertEntityToDto(Book book) {
        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .amountOfInstances(book.getAmountOfInstances())
                .releaseDate(book.getReleaseDate())
                .isAvailable(isAnyAvailable(book))
                .author(book.getAuthor())
                .build();
        return bookDto;
    }
}
