package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.*;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.*;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcExhibitDao implements ExhibitDao {

    private static JdbcExhibitDao instance;
    private final Connection connection;
    private AuthorDao authorDao;
    private final AudienceDao audienceDao;

    private JdbcExhibitDao(Connection connection, AudienceDao audienceDao) {
        this.connection = connection;
        this.audienceDao = audienceDao;
    }

    public static JdbcExhibitDao getInstance(Connection connection, AudienceDao audienceDao) {
        if (instance == null) {
            instance = new JdbcExhibitDao(connection, audienceDao);
        }

        return instance;
    }

    @Override
    public Optional<Exhibit> findByName(String name) {
        String FIND_EXHIBIT_BY_NAME = "SELECT id as exhibit_id, type as exhibit_type, material as exhibit_material," +
                "techic as exhibit_technique, name AS exhibit_name FROM exhibits WHERE name = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXHIBIT_BY_NAME, new ExhibitRowMapper(), name);
    }

    @Override
    public List<Exhibit> findByAuthor(Author author) {
        String FIND_EXHIBITS_BY_AUTHOR_ID =
                "SELECT e.id AS exhibit_id, e.type AS exhibit_type, e.material AS exhibit_material, " +
                        "e.techic AS exhibit_technique, e.name AS exhibit_name " +
                        "FROM exhibits as e " +
                        "INNER JOIN autor_exhibit as ae " +
                        "ON ae.exhibit_id = e.id " +
                        "WHERE ae.autor_id = ?";

        return JdbcUtils.query(connection, FIND_EXHIBITS_BY_AUTHOR_ID, new ExhibitRowMapper(), author.getId());
    }

    @Override
    public List<Exhibit> findByEmployee(Employee employee) {
        String FIND_EXHIBITS_BY_EMPLOYEE_ID =
                "SELECT e.id AS exhibit_id, e.type AS exhibit_type, e.material AS exhibit_material, " +
                        "e.techic AS exhibit_technique, e.name AS exhibit_name " +
                        "FROM exhibits as e " +
                        "INNER JOIN employees AS em " +
                        "ON e.audience_id = em.audience_id " +
                        "WHERE em.id = ?";

        return JdbcUtils.query(connection, FIND_EXHIBITS_BY_EMPLOYEE_ID, new ExhibitRowMapper(), employee.getId());
    }

    @Override
    public List<Exhibit> findByAudience(Audience audience) {
        String FIND_BY_EXHIBIT =
                "SELECT id AS exhibit_id, type AS exhibit_type, material AS exhibit_material, " +
                        "techic AS exhibit_technique, name AS exhibit_name " +
                        "FROM exhibits " +
                        "WHERE audience_id = ?";

        return JdbcUtils.query(connection, FIND_BY_EXHIBIT, new ExhibitRowMapper(), audience.getId());
    }

    @Override
    public void updateAudience(Exhibit exhibit, Audience audience) {
        String UPDATE_EXHIBIT_AUDIENCE = "UPDATE exhibits SET audience_id = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_EXHIBIT_AUDIENCE, audience.getId(), exhibit.getId());
    }

    @Override
    public void addAuthor(Exhibit exhibit, Author author) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) values(?, ?)";

        JdbcUtils.update(connection, ADD_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    @Override
    public void deleteAuthor(Exhibit exhibit, Author author) {
        String DELETE_EXHIBIT_AUTHOR = "DELETE FROM autor_exhibit WHERE autor_id = ? and exhibit_id = ?";

        JdbcUtils.update(connection, DELETE_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    @Override
    public Map<Audience, List<Exhibit>> findAllGroupedByAudience() {
        String FIND_EXHIBIT_WITH_AUDIENCE =
                "SELECT e.id AS exhibit_id, e.type AS exhibit_type, e.material AS exhibit_material, e.techic AS exhibit_technique, " +
                        "e.name as exhibit_name, a.id AS audience_id, a.name as audience_name " +
                        "FROM exhibits AS e " +
                        "INNER JOIN audiences AS a " +
                        "ON e.audience_id = a.id";

        return JdbcUtils.queryForObject(connection, FIND_EXHIBIT_WITH_AUDIENCE, new GroupedAudienceMapper()).orElse(null);
    }

    @Override
    public long save(Exhibit objectToSave) {
        String SAVE_EXHIBIT = "INSERT INTO exhibits(type, material, techic, name) VALUES (?, ?, ?, ?)";

        return JdbcUtils.update(connection, SAVE_EXHIBIT, objectToSave.getType().toString(), objectToSave.getMaterial(),
                objectToSave.getTechnique(), objectToSave.getName());
    }

    @Override
    public void deleteById(long id) {
        String DELETE_EXHIBIT_BY_ID = "DELETE FROM exhibits WHERE id = ?";

        JdbcUtils.update(connection, DELETE_EXHIBIT_BY_ID, id);
    }

    @Override
    public Optional<Exhibit> findById(long id) {
        String FIND_EXHIBIT_BY_ID = "SELECT id AS exhibit_id, type AS exhibit_type, material AS exhibit_material, " +
                "techic AS exhibit_technique, name AS exhibit_name FROM exhibits WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXHIBIT_BY_ID, new ExhibitRowMapper(), id);
    }

    @Override
    public List<Exhibit> findAll() {
        String FIND_ALL_EXHIBITS = "SELECT id AS exhibit_id, type AS exhibit_type, material AS exhibit_material, " +
                "techic AS exhibit_technique, name AS exhibit_name FROM exhibits";

        return JdbcUtils.query(connection, FIND_ALL_EXHIBITS, new ExhibitRowMapper());
    }

    @Override
    public int update(Exhibit newObject) {
        String UPDATE_EXHIBIT = "UPDATE exhibits SET type = ?, material = ?, techic = ?, name = ? where id = ?";

        return JdbcUtils.update(connection, UPDATE_EXHIBIT, newObject.getType().toString(), newObject.getMaterial(),
                newObject.getTechnique(), newObject.getName(), newObject.getId());
    }

    @Override
    public ExhibitStatistic findStatistic() {
        ExhibitStatistic statistic = new ExhibitStatistic();
        statistic.setMaterialStatistic(findStatisticByType(ExhibitType.SCULPTURE, "material"));
        statistic.setTechniqueStatistic(findStatisticByType(ExhibitType.PAINTING, "techic"));

        return statistic;
    }

    @Override
    public Exhibit loadForeignFields(Exhibit exhibit) {
        exhibit.setAuthors(authorDao.findByExhibit(exhibit));
        exhibit.setAudience(audienceDao.findByExhibit(exhibit).orElse(null));

        return exhibit;
    }

    private Map<String, Integer> findStatisticByType(ExhibitType type, String statisticField) {
        String STATISTIC_BY_MATERIAL =
                "SELECT " + statisticField + ", count(id) as number " +
                        "FROM exhibits " +
                        "WHERE type = ? " +
                        "GROUP BY " + statisticField;

        return JdbcUtils.queryForObject(connection, STATISTIC_BY_MATERIAL, new ExhibitTypeStatisticMapper(statisticField),
                type.toString()).orElse(null);
    }

    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }
}
