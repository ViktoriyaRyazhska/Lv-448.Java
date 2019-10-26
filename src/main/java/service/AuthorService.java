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

    /**
     * Default constructor
     */
    public AuthorService() {
        this.authorDao = DaoFactory.authorDao();
        this.userService = new UserService();
    }

    /**
     * Method for creating an address in database
     *
     * @param author address that must be created
     */
    public void createAuthor(Author author) {
        authorDao.save(author);
    }

    /**
     * Method for updating address in database
     *
     * @param author address for updating
     * @return true if author was updated
     * or false if he doesn't exist in database
     */
    public boolean updateAuthor(Author author) {
        if (authorDao.findById(author.getId()).isPresent()) {
            authorDao.update(author);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for finding an author by his id
     *
     * @param id author's id
     * @return Author object
     */
    public Author findAuthorById(Long id) {
        return authorDao.findById(id).get();
    }

    /**
     * Method for finding all Authors by book id
     *
     * @param bookId book's id
     * @return list of authorsDto by book id
     */
    public List<AuthorDto> findAllSubAuthorByBookId(Long bookId) {
        return authorDao.findAllSubAuthorByBookId(bookId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Method used for finding all authors
     *
     * @return list of  all authors
     */
    public List<AuthorDto> findAllAuthors() {
        return authorDao.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     *Method used for finding all author by surname
     *
     * @param surname author's surname
     * @return list of authors by their surnames
     */
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
