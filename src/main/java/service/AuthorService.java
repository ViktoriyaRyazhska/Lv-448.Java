package service;

import dao.implemetsDao.AuthorDao;
import entities.Author;

import java.util.List;

public class AuthorService {
    private AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public List<Author> findAllAuthors() {
        return authorDao.findAll();
    }

    public Author findAuthorBySurname(String surname) throws IllegalAccessException {
        if (authorDao.findBySurname(surname).isPresent()) {
            return authorDao.findBySurname(surname).get();
        } else {
            throw new IllegalAccessException("Author not found");
        }
    }

    public boolean updateAuthor(Author author) {
        if (authorDao.findById(author.getId()).isPresent()) {
            authorDao.update(author);
            return true;
        } else {
            return false;
        }
    }

    public void createAuthor(Author author) {
        authorDao.save(author);
    }

    public Author findAuthorById(Long id) {
        return authorDao.findById(id).get();
    }
}
