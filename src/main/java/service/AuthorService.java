package service;

import dto.AuthorDto;
import dao.interfaceDao.AuthorDaoInterface;
import database.DaoFactory;
import entities.Author;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorService {
    private AuthorDaoInterface authorDao;
    private UserService userService;

    public AuthorService() {
        this.authorDao = DaoFactory.authorDao();
        this.userService = new UserService();
    }

    public void createAuthor(Author author) {
        authorDao.save(author);
    }

    public boolean updateAuthor(Author author) {
        if (authorDao.findById(author.getId()).isPresent()) {
            authorDao.update(author);
            return true;
        } else {
            return false;
        }
    }

    public Author findAuthorById(Long id) {
        return authorDao.findById(id).get();
    }

    public List<AuthorDto> findAllSubAuthorByBookId(Long bookId) {
        return authorDao.findAllSubAuthorByBookId(bookId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<AuthorDto> findAllAuthors() {
        return authorDao.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<AuthorDto> findAuthorBySurname(String surname) {
        if (!authorDao.findBySurname(surname).isEmpty()) {
            return authorDao.findBySurname(surname).stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Author not found");
        }
    }

    private AuthorDto convertEntityToDto(Author author) {
        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getAuthorFirstName())
                .surname(author.getAuthorLastName())
                .averageAgeUser(userService.averageAgeUsersByAuthor(author.getId()))
                .build();
        return authorDto;
    }
}
