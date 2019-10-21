package academy.softserve.dao.impl.jdbc;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcAuthorDaoTest extends JdbcDaoTest {
    private AuthorDao authorDao;
    private ExhibitDao exhibitDao;
    private List<Author> authors;

    @BeforeEach
    void init() {
        dropTables();
        createTables();
        fillTables();
        authorDao = jdbcAuthorDao();
        exhibitDao = jdbcExhibitDao();

        authors = new ArrayList<>(Arrays.asList(
                new Author(1, "Leonardo", "da Vinci"),
                new Author(2, "Francesco", "Melzi"),
                new Author(3, "Bernardino", "Luini"),
                new Author(4, "Auguste", "Rodin")));
    }

    @Test
    void findAll() {
        assertEquals(authors, authorDao.findAll());
    }

    @Test
    void findByExistingId() {
        Author expected = new Author(3, "Bernardino", "Luini");

        assertAuthorEquals(expected, authorDao.findById(3).orElse(null));
    }

    @Test
    void findByNotExistingId() {
        assertNull(authorDao.findById(100500).orElse(null));
    }

    @Test
    public void updateExisting() {
        Author expected = new Author(1, "Leonardo", "Dicaprio");
        authorDao.update(expected);

        Author actual = authorDao.findById(1).orElse(null);

        assertAuthorEquals(expected, actual);
    }

    @Test
    public void updateNotExisting() {
        Author expected = new Author(100500, "TEST_FIRST_NAME", "TEST_LAST_NAME");
        authorDao.update(expected);
    }

    @Test
    void deleteByExistingId() {
        int listIndex = 0;

        authorDao.deleteById(authors.get(listIndex).getId());

        List<Author> expected = authors;
        authors.remove(listIndex);

        assertAuthorEquals(expected, authorDao.findAll());
    }

    @Test
    void deleteByNotExistingId() {
        authorDao.deleteById(100500);
    }

    @Test
    void save() {
        Author newAuthor = new Author(authors.size() + 1, "TEST_FIRST_NAME", "TEST_LAST_NAME");

        assertEquals(authors.size() + 1, authorDao.save(newAuthor));

        assertAuthorEquals(newAuthor, authorDao.findById(authors.size() + 1).orElse(null));
    }

    @Test
    void findByExistingFullName() {
        String fName = "Leonardo";
        String lName = "da Vinci";

        Author expected = new Author(1, fName, lName);

        assertAuthorEquals(expected, authorDao.findByFullName(fName, lName).orElse(null));
    }

    @Test
    void findByNotExistingFullName() {
        String fName = "NOT_EXISTING_FIRST_NAME";
        String lName = "NOT_EXISTING_SECOND_NAME";

        assertEquals(Optional.empty(), authorDao.findByFullName(fName, lName));
    }

    @Test
    void deleteExistingExhibitAuthor() {
        Exhibit exhibit = new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa");
        Author author = new Author(1, "Leonardo", "da Vinci");
        List<Exhibit> expected = Arrays.asList(
                new Exhibit(2, ExhibitType.PAINTING, null, "Oils", "Madonna Litta"),
                new Exhibit(3, ExhibitType.PAINTING, null, "Watercolor", "Female Saint")
        );

        authorDao.deleteAuthor(author, exhibit);
        authorDao.loadForeignFields(author);

        assertExhibitEquals(expected, author.getExhibits());
    }

    @Test
    void deleteNotExistingExhibitAuthor() {
        Exhibit exhibit = new Exhibit(100500, ExhibitType.PAINTING, null, null, null);
        Author author = new Author(1, "Leonardo", "da Vinci");
        authorDao.deleteAuthor(author, exhibit);
    }

    @Test
    void deleteExistingExhibitNotExistingAuthor() {
        Exhibit exhibit = new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa");
        Author author = new Author(100500, "Leonardo", "da Vinci");
        authorDao.deleteAuthor(author, exhibit);
    }

    @Test
    void addExistingExhibitAuthor() {
        Author author = new Author(1, "Leonardo", "da Vinci");
        Exhibit exhibit = new Exhibit(4, ExhibitType.PAINTING, null, "Pencil", "Flora");
        List<Exhibit> expected = new ArrayList<>(Arrays.asList(
                new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa"),
                new Exhibit(2, ExhibitType.PAINTING, null, "Oils", "Madonna Litta"),
                new Exhibit(3, ExhibitType.PAINTING, null, "Watercolor", "Female Saint")));

        assertExhibitEquals(expected, exhibitDao.findByAuthor(author));

        authorDao.addAuthor(author, exhibit);

        expected.add(exhibit);

        assertExhibitEquals(expected, exhibitDao.findByAuthor(author));
    }

    @Test
    void addNotExistingExhibitAuthor() {
        Author author = new Author(100500, null, null);
        Exhibit exhibit = new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa");

        assertThrows(RuntimeException.class, () -> exhibitDao.addAuthor(exhibit, author));
    }

    @Test
    void findByExistingExhibit() {
        Exhibit exhibit = new Exhibit(3, ExhibitType.PAINTING, null, "Watercolor", "Female Saint");
        List<Author> expected = new ArrayList<>(Arrays.asList(
                new Author(1, "Leonardo", "da Vinci"),
                new Author(2, "Francesco", "Melzi")));

        assertAuthorEquals(expected, authorDao.findByExhibit(exhibit));
    }

    @Test
    void findByNotExistingExhibit() {
        Exhibit exhibit = new Exhibit(100500, null, null, null, null);

        assertEquals(Collections.emptyList(), authorDao.findByExhibit(exhibit));
    }

    @Test
    void loadForeignField() {
        Author author = new Author(1, "Leonardo", "da Vinci");
        Author expected = new Author(1, "Leonardo", "da Vinci");
        List<Exhibit> expectedExhibits = new ArrayList<>(Arrays.asList(
                new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa"),
                new Exhibit(2, ExhibitType.PAINTING, null, "Oils", "Madonna Litta"),
                new Exhibit(3, ExhibitType.PAINTING, null, "Watercolor", "Female Saint")));

        expected.setExhibits(expectedExhibits);

        authorDao.loadForeignFields(author);

        assertAuthorEquals(author, expected);
        assertExhibitEquals(expectedExhibits, author.getExhibits());
    }
}
