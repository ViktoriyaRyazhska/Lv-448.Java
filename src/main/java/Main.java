import connection.ConnectDB;
import dao.implemetsDao.BookDao;
import dao.implemetsDao.BookInstanceDao;
import entities.BookInstance;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectDB.getConnection();


        BookInstanceDao bookInstanceDao = new BookInstanceDao(connection);
        BookDao bookDao = new BookDao(connection);
        BookInstance build = BookInstance.builder().id(11L).isAvailable(true).build();


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
        System.out.println(bookInstanceDao.findAllBookInstanceByTitle("Book3"));


    }
}
