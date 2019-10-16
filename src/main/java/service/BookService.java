package service;

import dao.implemetsDao.BookDao;
import entities.Author;
import entities.Book;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private BookDao bookDao;

    public List<Book> findBookByAuthor(Author author) {
        return bookDao.findAllByAuthorName(author.getId());
    }


}
