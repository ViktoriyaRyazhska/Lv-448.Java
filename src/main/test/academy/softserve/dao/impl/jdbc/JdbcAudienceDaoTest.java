package academy.softserve.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.entities.Audience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcAudienceDaoTest extends JdbcDaoTest {
    private AudienceDao audienceDao;
    private List<Audience> audiences;

    @BeforeEach
    void init() {
        dropTables();
        createTables();
        fillTables();
        audienceDao = jdbcAudienceDao();

        audiences = new ArrayList<>(Arrays.asList(
                new Audience(1, "Leonardo da Vinci"),
                new Audience(2, "Pictures of the Middle Ages"),
                new Audience(3, "Sculptures")
        ));
    }

    @Test
    void save() {
        Audience expected = new Audience(4, "TEST_AUDIENCE_NAME");

        audienceDao.save(expected);

        assertAudienceEquals(expected, audienceDao.findById(4).orElse(null));
    }

    @Test
    void deleteByExistingId() {
        int index = 0;

        audienceDao.deleteById(index + 1);
        audiences.remove(audiences.get(index));

        assertAudienceEquals(audiences, audienceDao.findAll());
    }

    @Test
    void deleteByNotExistingId() {
        audienceDao.deleteById(100500);
    }

    @Test
    void findByExistingId() {
        int id = 3;
        Audience expected = new Audience(3, "Sculptures");

        assertAudienceEquals(expected, audienceDao.findById(id).orElse(null));
    }

    @Test
    void findByNotExistingId() {
        int id = 100500;

        assertEquals(Optional.empty(), audienceDao.findById(id));
    }

    @Test
    void findAll() {
        assertAudienceEquals(audiences, audienceDao.findAll());
    }

    @Test
    void updateExisting() {
        Audience newAudience = new Audience(2, "Middle Ages Pictures");

        audienceDao.update(newAudience);

        assertAudienceEquals(newAudience, audienceDao.findById(2).orElse(null));
    }

    @Test
    void updateNotExisting() {
        Audience newAudience = new Audience(100500, null);

        audienceDao.update(newAudience);
    }

    @Test
    void findByExistingName(){
        String name = "Leonardo da Vinci";
        Audience expected = new Audience(1, "Leonardo da Vinci");

        assertAudienceEquals(expected, audienceDao.findByName(name).orElse(null));
    }

    @Test
    void findByNotExistingName(){
        String name = "NOT_EXISTING_NAME";

        assertEquals(Optional.empty(), audienceDao.findByName(name));
    }
}
