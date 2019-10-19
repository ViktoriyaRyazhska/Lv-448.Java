import connection.ConnectDB;
import dao.implemetsDao.AuthorDao;
import dao.implemetsDao.BookDao;
import dao.implemetsDao.BookInstanceDao;
import dao.implemetsDao.UserDao;
import entities.Author;
import entities.Book;
import entities.BookInstance;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectDB.getConnection();

        AuthorDao authorDao = new AuthorDao(connection);
        BookDao bookDao = new BookDao(connection, authorDao);
        BookInstanceDao bookInstanceDao = new BookInstanceDao(connection, bookDao);
        BookInstance build = BookInstance.builder().id(11L).isAvailable(true).build();
        UserDao userDao = new UserDao(connection);

        //        bookInstanceDao.save(build);
//        System.out.println(bookInstanceDao.findById(3L));
//        System.out.println(bookInstanceDao.findAll());
//        System.out.println(bookInstanceDao.isAvailable(1L));
//        System.out.println(bookInstanceDao.findAllBookInstanceByTitle("Book3"));
//        System.out.println(bookDao.getInfoByBookInstance(1L));
        LocalDate dateFrom = Date.valueOf("1970-01-01").toLocalDate();
        LocalDate dateTo = Date.valueOf("2020-01-01").toLocalDate();
//        Map<Long, Long> bookInstanceIdAndCountOrderedByPeriod =
//                bookInstanceDao.findBookInstanceIdAndCountOrderedByPeriod(dateFrom, dateTo);
//        for (Map.Entry<Long, Long> entry : bookInstanceIdAndCountOrderedByPeriod.entrySet()) {
//            System.out.println(bookDao.getInfoByBookInstance(entry.getKey()).getTitle()+ " count: " + entry.getValue());
//        }
//        System.out.println(bookInstanceDao.findAll());
//        System.out.println(bookInstanceDao.findAllBookInstanceByTitle("Book3"));
        LocalDate now = LocalDate.now();
//        User build1 = User.builder()
//                .email("email@test.ua")
//                .birthday(Date.valueOf("1988-10-19").toLocalDate())
//                .phoneNumber("+380980000000")
//                .userName("Bogdan")
//                .userSurname("Kuzenko")
//                .build();
////        userDao.save(build1);
//        List<User> all = userDao.findAll();
//        all.stream().forEach(System.out::println);
//        Author build1 = Author.builder().authorFirstName("Taras").authorLastName("SHeva").build();
        Optional<Author> byId = authorDao.findById(1L);
        Book book = Book.builder().amountOfInstances(10).releaseDate(dateFrom).title("Kobzar").author(byId.get()).build();
////        bookDao.save(book);
//        Optional<Book> byId = bookDao.findById(5L);
//        System.out.println(byId.get().toString());
//        bookDao.update(1L, book);
//        Optional<Book> byId1 = bookDao.findById(1L);
//        BookInstance build1 = BookInstance.builder().isAvailable(true).book(byId1.get()).build();
//        bookInstanceDao.save(build1);
        Optional<BookInstance> byId1 = bookInstanceDao.findById(1L);
        System.out.println(byId1.get().toString());

    }
}
