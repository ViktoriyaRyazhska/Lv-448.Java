import dao.implemetsDao.AuthorDao;
import database.DaoFactory;
import entities.Author;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthorDao authorDao = DaoFactory.authorDao();
        List<Author> all = authorDao.findAll();
        all.stream().forEach(System.out::println);
    }
}
