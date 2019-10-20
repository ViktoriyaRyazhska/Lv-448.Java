import connection.ConnectDB;
import dao.implemetsDao.*;
import entities.Address;
import entities.BookInstance;
import entities.User;
import service.BookService;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectDB.getConnection();
        AuthorDao authorDao = new AuthorDao(connection);
        BookDao bookDao = new BookDao(connection, authorDao);
        BooksSubAuthors booksSubAuthors = new BooksSubAuthors(connection, authorDao, bookDao);
        BookService bookService = new BookService(authorDao, bookDao, booksSubAuthors);

//        List<Author> allSunAuthorByBookId = booksSubAuthors.findAllSunAuthorByBookId(1L);
//        allSunAuthorByBookId.stream().forEach(System.out::println);
//        List<Book> allBooksBySubAuthor = bookDao.findAllBooksBySubAuthor(1L);
//        allBooksBySubAuthor.stream().forEach(System.out::println);

//        List<Book> allBooksBySubAuthor = bookService.findAllBooksBySubAuthor(1L);
//        allBooksBySubAuthor.stream().forEach(System.out::println);
//        List<Book> allBooksIdBySubAuthorId = booksSubAuthors.findAllBooksIdBySubAuthorId(1L);
//        allBooksIdBySubAuthorId.stream().forEach(System.out::println);
//        List<Book> allBooksBySubAuthors = bookService.findAllBooksBySubAuthor(1L);
//        allBooksBySubAuthors.stream().forEach(System.out::println);
//        List<Book> allBooksIdBySubAuthorId = booksSubAuthors.findAllBooksIdBySubAuthorId(1L);
//        allBooksIdBySubAuthorId.stream().forEach(System.out::println);
//        List<Book> allBooksBySubAuthor = bookDao.findAllBooksBySubAuthor(1L);
//        allBooksBySubAuthor.stream().forEach(System.out::println);
//        List<Book> allBooksBySubAuthor = bookService.findAllBooksBySubAuthor(1L);
//        allBooksBySubAuthor.stream().forEach(System.out::println);
//        List<Book> allBooksBySubAuthorId = booksSubAuthors.findAllBooksBySubAuthorId(1L);
//        allBooksBySubAuthorId.stream().forEach(System.out::println);
//        booksSubAuthors.setSubAuthorForBook(1L, 2L);
//        booksSubAuthors.deleteSubAuthor(1L, 2L);

        UserDao userDao = new UserDao(connection);
        Address build = Address.builder().id(1L).city("Lviv").street("Gorodocka").buildingNumber(12)
                .apartment(1).build();
        User build1 = User.builder().id(2L).userSurname("Ольховський").userName("Юра").phoneNumber("+380930004455")
                .birthday(Date.valueOf("1800-02-01").toLocalDate())
                .email("wwww4@mail.ru")
                .registrationDate(Date.valueOf(LocalDate.now()).toLocalDate())
                .userAddress(build).build();
//        userDao.save(build1);
//        LocalDate date1 = Date.valueOf("1990-01-01").toLocalDate();
//        LocalDate date2 = Date.valueOf("2020-01-01").toLocalDate();
//        Integer integer = userDao.averageAmountOfOrdersBySomePeriod(date1, date2);
//        System.out.println(integer);

//        userDao.update(build1);
//
//        User user = userDao.findById(2L).get();
//        System.out.println(user.toString());

        BookInstanceDao bookInstanceDao = new BookInstanceDao(connection, bookDao);
        List<BookInstance> allBookInstanceOnReading = bookInstanceDao.findAllReturnedBookInstanceByUser(5L);
        allBookInstanceOnReading.stream().forEach(System.out::println);


    }
}
