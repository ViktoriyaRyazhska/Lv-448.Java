package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.*;
import academy.softserve.museum.entities.*;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.util.JdbcUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class, that contains special methods for
 * getting / updating Exhibit objects from /in database.
 */
public class JdbcExhibitDao implements ExhibitDao {

    /**
     * this field used for implementing Singleton.
     */
    private static JdbcExhibitDao instance;
    /**
     * Connection used for interaction with database.
     */
    private final Connection connection;
    /**
     * this field used for loading and setting Authors for Exhibit.
     */
    private AuthorDao authorDao;
    /**
     * this field used for loading and setting Audience for Exhibit.
     */
    private final AudienceDao audienceDao;

    /**
     * Constructor, that creates instance using Connection and AudienceDao.
     *
     * @param connection  Connection used for interaction with database
     * @param audienceDao AudienceDao used for loading and setting Audience for Exhibit.                  .
     */
    private JdbcExhibitDao(Connection connection, AudienceDao audienceDao) {
        this.connection = connection;
        this.audienceDao = audienceDao;
    }

    /**
     * Method for getting instance of JdbcExhibitDao.
     *
     * @param connection  Connection, that used for interaction with database.
     * @param audienceDao AudienceDao used for loading and setting Audience for Exhibit.
     * @return instance of JdbcExcursionDao.
     */
    public synchronized static JdbcExhibitDao getInstance(Connection connection, AudienceDao audienceDao) {
        if (instance == null) {
            instance = new JdbcExhibitDao(connection, audienceDao);
        }

        return instance;
    }

    /**
     * Method for finding Exhibit by name.
     * Method doesn't return Exhibit with loaded Audience and Authors.
     * To load Audience and Authors you need to call loadForeignFields method.
     *
     * @param name name of Exhibit.
     * @return Exhibit object wrapped in Optional or
     * Optional.empty() if there is no Exhibit with
     * such name.
     */
    @Override
    public Optional<Exhibit> findByName(String name) {
        String FIND_EXHIBIT_BY_NAME = "SELECT id as exhibit_id, type as exhibit_type, material as exhibit_material," +
                "techic as exhibit_technique, name AS exhibit_name FROM exhibits WHERE name = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXHIBIT_BY_NAME, new ExhibitRowMapper(), name);
    }

    /**
     * Method for finding Exhibits by Author.
     * Method doesn't return Exhibit with loaded Audience and Authors.
     * To load Audience and Authors you need to call loadForeignFields method.
     *
     * @param author Author of Exhibit.
     * @return list of Exhibits that have
     * given Author.
     */
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

    /**
     * Method for finding Exhibits by Author,
     * that linked with Audience where Exhibit is.
     * Method doesn't return Exhibit with loaded Audience and Authors.
     * To load Audience and Authors you need to call loadForeignFields method.
     *
     * @param employee Employee for which you want
     *                 find Exhibits.
     * @return list of Exhibits.
     */
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

    /**
     * Method for finding Exhibits by Audience.
     * Method doesn't return Exhibit with loaded Audience and Authors.
     * To load Audience and Authors you need to call loadForeignFields method.
     *
     * @param audience Audience for which you want
     *                 to find Exhibits.
     * @return list of Exhibits located in given Audience.
     */
    @Override
    public List<Exhibit> findByAudience(Audience audience) {
        String FIND_BY_EXHIBIT =
                "SELECT id AS exhibit_id, type AS exhibit_type, material AS exhibit_material, " +
                        "techic AS exhibit_technique, name AS exhibit_name " +
                        "FROM exhibits " +
                        "WHERE audience_id = ?";

        return JdbcUtils.query(connection, FIND_BY_EXHIBIT, new ExhibitRowMapper(), audience.getId());
    }

    /**
     * Method for updating Audience where Exhibit located.
     *
     * @param exhibit  Exhibit for which you want to update
     *                 Audience.
     * @param audience new Audience for given Exhibit.
     */
    @Override
    public void updateAudience(Exhibit exhibit, Audience audience) {
        String UPDATE_EXHIBIT_AUDIENCE = "UPDATE exhibits SET audience_id = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_EXHIBIT_AUDIENCE, audience.getId(), exhibit.getId());
    }

    /**
     * Method for adding Author for given Exhibit.
     *
     * @param exhibit Exhibit for which you want to add
     *                Author.
     * @param author  new Author for Exhibit.
     */
    @Override
    public void addAuthor(Exhibit exhibit, Author author) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) values(?, ?)";

        JdbcUtils.update(connection, ADD_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    /**
     * Method for deleting Author for given Exhibit.
     *
     * @param exhibit Exhibit for which you want to
     *                delete Author.
     * @param author  Author to delete.
     */
    @Override
    public void deleteAuthor(Exhibit exhibit, Author author) {
        String DELETE_EXHIBIT_AUTHOR = "DELETE FROM autor_exhibit WHERE autor_id = ? and exhibit_id = ?";

        JdbcUtils.update(connection, DELETE_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    /**
     * Method for finding all Exhibits grouped by Audience.
     *
     * @return Map with Audience as key and List of Exhibits in
     * that Audience as a value.
     */
    @Override
    public Map<Audience, List<Exhibit>> findAllGroupedByAudience() {
        String FIND_EXHIBIT_WITH_AUDIENCE =
                "SELECT e.id AS exhibit_id, e.type AS exhibit_type, e.material AS exhibit_material, e.techic AS exhibit_technique, " +
                        "e.name as exhibit_name, a.id AS audience_id, a.name as audience_name " +
                        "FROM exhibits AS e " +
                        "INNER JOIN audiences AS a " +
                        "ON e.audience_id = a.id";

        return JdbcUtils.queryForObject(connection, FIND_EXHIBIT_WITH_AUDIENCE, new GroupedExhibitMapper()).orElse(null);
    }

    /**
     * Method for saving Exhibits in database.
     *
     * @param objectToSave Exhibit, that must be saved.
     * @return id of last saved Exhibit.
     */
    @Override
    public long save(Exhibit objectToSave) {
        String SAVE_EXHIBIT = "INSERT INTO exhibits(type, material, techic, name) VALUES (?, ?, ?, ?)";

        JdbcUtils.update(connection, SAVE_EXHIBIT, objectToSave.getType().toString(), objectToSave.getMaterial(),
                objectToSave.getTechnique(), objectToSave.getName());

        return getLastSavedObjectId();
    }

    /**
     * Method for deleting Exhibits by id.
     *
     * @param id id of Exhibit, that must be deleted.
     */
    @Override
    public void deleteById(long id) {
        String DELETE_EXHIBIT_BY_ID = "DELETE FROM exhibits WHERE id = ?";

        JdbcUtils.update(connection, DELETE_EXHIBIT_BY_ID, id);
    }

    /**
     * Method, that returns Exhibit wrapped in Optional by id.
     * Method doesn't return Exhibit with loaded Audience and Authors.
     * To load Audience and Authors you need to call loadForeignFields method.
     *
     * @param id object's id.
     * @return Exhibit wrapped in Optional that has id field value
     * the same as id parameter value.
     * If there is no object with such id value
     * it returns Optional.empty
     */
    @Override
    public Optional<Exhibit> findById(long id) {
        String FIND_EXHIBIT_BY_ID = "SELECT id AS exhibit_id, type AS exhibit_type, material AS exhibit_material, " +
                "techic AS exhibit_technique, name AS exhibit_name FROM exhibits WHERE id = ?";

        return JdbcUtils.queryForObject(connection, FIND_EXHIBIT_BY_ID, new ExhibitRowMapper(), id);
    }

    /**
     * Method, that returns all Exhibits from database
     * Method doesn't return Exhibits with loaded Audience and Authors.
     * To load Audience and Authors you need to call loadForeignFields method.
     *
     * @return list of Exhibits from database
     */
    @Override
    public List<Exhibit> findAll() {
        String FIND_ALL_EXHIBITS = "SELECT id AS exhibit_id, type AS exhibit_type, material AS exhibit_material, " +
                "techic AS exhibit_technique, name AS exhibit_name FROM exhibits";

        return JdbcUtils.query(connection, FIND_ALL_EXHIBITS, new ExhibitRowMapper());
    }

    /**
     * Method, that updates given object in database.
     * This method ignores audience and authors fields.
     * To update audience call updateAudience() method.
     * To update authors call addAuthor() or deleteAuthor()
     *
     * @param newObject Exhibit to update
     * @return changed rows count in database.
     */
    @Override
    public int update(Exhibit newObject) {
        String UPDATE_EXHIBIT = "UPDATE exhibits SET type = ?, material = ?, techic = ?, name = ? where id = ?";

        return JdbcUtils.update(connection, UPDATE_EXHIBIT, newObject.getType().toString(), newObject.getMaterial(),
                newObject.getTechnique(), newObject.getName(), newObject.getId());
    }

    /**
     * Method for finding statistic for all Exhibits.
     * Statistic contains map with materials and their count
     * and map with techniques and their count.
     *
     * @return statistic.
     */
    @Override
    public ExhibitStatistic findStatistic() {
        ExhibitStatistic statistic = new ExhibitStatistic();
        statistic.setMaterialStatistic(findStatisticByType(ExhibitType.SCULPTURE, "material"));
        statistic.setTechniqueStatistic(findStatisticByType(ExhibitType.PAINTING, "techic"));

        return statistic;
    }

    /**
     * Method for loading and setting Exhibit's Authors and Audience
     *
     * @param exhibit Exhibit for which you want
     *                to load and set Authors and Audience.
     * @return Given Exhibit with set Authors and Audience.
     */
    @Override
    public Exhibit loadForeignFields(Exhibit exhibit) {
        exhibit.setAuthors(authorDao.findByExhibit(exhibit));
        exhibit.setAudience(audienceDao.findByExhibit(exhibit).orElse(null));

        return exhibit;
    }

    /**
     * Method for finding statistic for all Exhibits.
     * Statistic contains map with given parameter and it's count.
     *
     * @param type           type of Exhibit for which statistic calculates.
     * @param statisticField parameter which is used to calculate
     *                       statistic.
     * @return calculated statistic.
     */
    private Map<String, Integer> findStatisticByType(ExhibitType type, String statisticField) {
        String STATISTIC_BY_MATERIAL =
                "SELECT " + statisticField + ", count(id) as number " +
                        "FROM exhibits " +
                        "WHERE type = ? " +
                        "GROUP BY " + statisticField;

        return JdbcUtils.queryForObject(connection, STATISTIC_BY_MATERIAL, new ExhibitTypeStatisticMapper(statisticField),
                type.toString()).orElse(null);
    }

    /**
     * Method for getting last saved Exhibit id.
     *
     * @return id of last saved Exhibit.
     */
    private long getLastSavedObjectId() {
        String QUERY = "SELECT MAX(id) AS last_id FROM exhibits";

        return JdbcUtils.queryForObject(connection, QUERY, new IdRowMapper()).
                orElseThrow(() -> new RuntimeException("Can't get last saved object id"));
    }

    /**
     * Setter for AuthorDao, that is used for loading
     * Authors for Exhibit.
     *
     * @param authorDao AuthorDao, that is used for loading
     *                  Authors for Exhibit.
     */
    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }
}
