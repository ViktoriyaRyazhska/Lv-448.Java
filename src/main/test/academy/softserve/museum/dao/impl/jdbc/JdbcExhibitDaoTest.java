package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.entities.*;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcExhibitDaoTest extends JdbcDaoTest {
    private JdbcExhibitDao exhibitDao;
    private JdbcAuthorDao authorDao;
    private List<Exhibit> exhibits;

    @BeforeEach
    void init() {
        dropTables();
        createTables();
        fillTables();
        exhibitDao = jdbcExhibitDao();
        authorDao = jdbcAuthorDao();

        exhibits = new ArrayList<>(Arrays.asList(
                new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa"),
                new Exhibit(2, ExhibitType.PAINTING, null, "Oils", "Madonna Litta"),
                new Exhibit(3, ExhibitType.PAINTING, null, "Watercolor", "Female Saint"),
                new Exhibit(4, ExhibitType.PAINTING, null, "Pencil", "Flora"),
                new Exhibit(5, ExhibitType.SCULPTURE, "Bronze", null, "A man with a broken nose"),
                new Exhibit(6, ExhibitType.SCULPTURE, "Stone", null, "Bronze Age"),
                new Exhibit(7, ExhibitType.SCULPTURE, "Clay", null, "Stone Age Gold"),
                new Exhibit(8, ExhibitType.SCULPTURE, "Bronze", null, "Clay Bronze Age")));
    }

    @Test
    void findAll() {
        assertExhibitEquals(exhibits, exhibitDao.findAll());
    }

    @Test
    void findByExistingId() {
        long id = 2;
        Exhibit expected = exhibits.get(1);

        assertExhibitEquals(expected, exhibitDao.findById(id).orElse(null));
    }

    @Test
    void findByNotExistingId() {
        assertNull(exhibitDao.findById(100500).orElse(null));
    }

    @Test
    public void updateExisting() {
        Exhibit expected = new Exhibit(4, ExhibitType.PAINTING, null, "TEST", "TEST");
        exhibitDao.update(expected);

        Exhibit actual = exhibitDao.findById(4).orElse(null);

        assertExhibitEquals(expected, actual);
    }

    @Test
    public void updateNotExisting() {
        Exhibit expected = new Exhibit(100500, ExhibitType.PAINTING, null, null, null);
        exhibitDao.update(expected);
    }

    @Test
    void deleteByExistingId() {
        long id = 1;

        exhibitDao.deleteById(id);

        List<Exhibit> expected = exhibits;
        expected.remove(0);

        assertExhibitEquals(expected, exhibitDao.findAll());
    }

    @Test
    void deleteByNotExistingId() {
        exhibitDao.deleteById(100500);
    }

    @Test
    void save() {
        Exhibit newExhibit = new Exhibit(9, ExhibitType.PAINTING, null, "TEST", "TEST");

        assertEquals(9, exhibitDao.save(newExhibit));

        assertExhibitEquals(newExhibit, exhibitDao.findById(9).orElse(null));
    }

    @Test
    void findByExistingName() {
        String name = "Bronze Age";
        Exhibit expected = new Exhibit(6, ExhibitType.SCULPTURE, "Stone", null, "Bronze Age");

        assertExhibitEquals(expected, exhibitDao.findByName(name).orElse(null));
    }

    @Test
    void findByNotExistingName() {
        String name = "TEST";

        assertNull(exhibitDao.findByName(name).orElse(null));
    }

    @Test
    void findByExistingAuthor() {
        Author author = new Author(1, "Leonardo", "da Vinci");
        List<Exhibit> expected = Arrays.asList(
                new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa"),
                new Exhibit(2, ExhibitType.PAINTING, null, "Oils", "Madonna Litta"),
                new Exhibit(3, ExhibitType.PAINTING, null, "Watercolor", "Female Saint"));

        assertExhibitEquals(expected, exhibitDao.findByAuthor(author));
    }

    @Test
    void findByNotExistingAuthor() {
        Author author = new Author(100500, null, null);

        assertExhibitEquals(Collections.emptyList(), exhibitDao.findByAuthor(author));
    }

    @Test
    void findByExistingEmployee() {
        Employee employee = new Employee(2, "Bogdan", "Korty", EmployeePosition.AUDIENCE_MANAGER,
                "Bogdan_Korty", "_Korty59");
        List<Exhibit> expected = Arrays.asList(
                exhibits.get(0),
                exhibits.get(1));

        assertExhibitEquals(expected, exhibitDao.findByEmployee(employee));
    }

    @Test
    void findByExistingAudience() {
        Audience audience = new Audience(3, "Sculptures");
        List<Exhibit> expected = Arrays.asList(
                exhibits.get(4),
                exhibits.get(5),
                exhibits.get(7));

        assertExhibitEquals(expected, exhibitDao.findByAudience(audience));
    }

    @Test
    void findByNotExistingAudience() {
        Audience audience = new Audience(100500, null);

        assertEquals(Collections.emptyList(), exhibitDao.findByAudience(audience));
    }

    @Test
    void updateAudience() {
        Exhibit exhibit = exhibits.get(3);
        Audience newAudience = new Audience(1, "Leonardo da Vinci");

        exhibitDao.updateAudience(exhibit, newAudience);

        assertAudienceEquals(newAudience, exhibitDao.loadForeignFields(exhibit).getAudience());
    }

    @Test
    void updateNotExistingAudience() {
        Exhibit exhibit = exhibits.get(3);
        Audience newAudience = new Audience(100500, null);

        assertThrows(RuntimeException.class, () -> exhibitDao.updateAudience(exhibit, newAudience));
    }

    @Test
    void addExistingAuthor() {
        Author author = new Author(1, "Leonardo", "da Vinci");
        Exhibit exhibit = exhibits.get(3);
        List<Author> expected = Arrays.asList(
                author,
                new Author(3, "Bernardino", "Luini"));

        exhibitDao.addAuthor(exhibit, author);

        assertAuthorEquals(expected, authorDao.findByExhibit(exhibit));
    }

    @Test
    void addNotExistingAuthor() {
        Author author = new Author(100500, null, null);
        Exhibit exhibit = exhibits.get(3);

        assertThrows(RuntimeException.class, () -> exhibitDao.addAuthor(exhibit, author));
    }

    @Test
    void deleteExistingAuthor() {
        Author author = new Author(3, "Bernardino", "Luini");
        Exhibit exhibit = exhibits.get(3);

        exhibitDao.deleteAuthor(exhibit, author);

        assertEquals(Collections.emptyList(), authorDao.findByExhibit(exhibit));
    }

    @Test
    void deleteNotExistingAuthor() {
        Author author = new Author(100500, null, null);
        Exhibit exhibit = exhibits.get(3);

        exhibitDao.deleteAuthor(exhibit, author);
    }

    @Test
    void findAllGroupedByAudience() {
        Map<Audience, List<Exhibit>> groupedByAudience = new HashMap<>();
        List<Exhibit> firstAudienceGroup = Arrays.asList(exhibits.get(0), exhibits.get(1));
        List<Exhibit> secondAudienceGroup = Arrays.asList(exhibits.get(2), exhibits.get(3), exhibits.get(6));
        List<Exhibit> thirdAudienceGroup = Arrays.asList(exhibits.get(4), exhibits.get(5), exhibits.get(7));

        groupedByAudience.put(new Audience(1, "Leonardo da Vinci"), firstAudienceGroup);
        groupedByAudience.put(new Audience(2, "Pictures of the Middle Ages"), secondAudienceGroup);
        groupedByAudience.put(new Audience(3, "Sculptures"), thirdAudienceGroup);

        assertEquals(groupedByAudience, exhibitDao.findAllGroupedByAudience());
    }

    @Test
    void findStatistic() {
        Map<String, Integer> techniqueStatistic = new HashMap<>();
        Map<String, Integer> materialStatistic = new HashMap<>();
        ExhibitStatistic actual;

        actual = exhibitDao.findStatistic();

        techniqueStatistic.put("Oils", 2);
        techniqueStatistic.put("Watercolor", 1);
        techniqueStatistic.put("Pencil", 1);

        materialStatistic.put("Bronze", 2);
        materialStatistic.put("Stone", 1);
        materialStatistic.put("Clay", 1);

        assertEquals(techniqueStatistic, actual.getTechniqueStatistic());
        assertEquals(materialStatistic, actual.getMaterialStatistic());
    }

    @Test
    void loadForeignFields() {
        Exhibit expected = exhibits.get(0);
        List<Author> expectedAuthors;
        Audience expectedAudience;

        expectedAuthors = Collections.singletonList(new Author(1, "Leonardo", "da Vinci"));
        expectedAudience = new Audience(1, "Leonardo da Vinci");
        expected.setAudience(expectedAudience);
        expected.setAuthors(expectedAuthors);

        Exhibit actual = new Exhibit(1, ExhibitType.PAINTING, null, "Oils", "Mona Lisa");

        exhibitDao.loadForeignFields(actual);

        assertExhibitEquals(expected, actual);
        assertAuthorEquals(expected.getAuthors(), actual.getAuthors());
        assertAudienceEquals(expected.getAudience(), actual.getAudience());
    }
}
