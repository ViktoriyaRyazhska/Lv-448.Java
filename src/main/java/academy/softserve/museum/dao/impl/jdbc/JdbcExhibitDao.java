package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AudienceRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.AuthorRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExhibitRowMapper;
import academy.softserve.museum.entities.*;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcExhibitDao implements ExhibitDao {

    private Connection connection;

    public JdbcExhibitDao(Connection connection) {
        this.connection = connection;
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
    public List<Author> findAuthorsByExhibit(Exhibit exhibit) {
        String FIND_AUTHORS_BY_EXHIBIT_ID =
                "SELECT a.id AS author_id, a.first_name AS author_first_name, a.last_name AS author_last_name" +
                        "FROM autors AS a " +
                        "INNER JOIN autor_exhibit AS ae " +
                        "ON a.id = ae.autor_id " +
                        "WHERE ae.exhibit_id = ? ";

        return JdbcUtils.query(connection, FIND_AUTHORS_BY_EXHIBIT_ID, new AuthorRowMapper(), exhibit.getId());
    }

    @Override
    public Audience findAudienceByExhibit(Exhibit exhibit) {
        String FIND_AUDIENCE_BY_EXHIBIT_ID =
                "select id AS audience_id, name AS audience_name " +
                        "FROM audiences " +
                        "WHERE id = (SELECT audience_id FROM exhibits WHERE id = ?);";

        return JdbcUtils.queryForObject(connection, FIND_AUDIENCE_BY_EXHIBIT_ID, new AudienceRowMapper(), exhibit.getId()).get();
    }

    @Override
    public void updateExhibitAudience(Exhibit exhibit, Audience audience) {
        String UPDATE_EXHIBIT_AUDIENCE = "UPDATE exhibits SET audience_id = ? WHERE id = ?";

        JdbcUtils.update(connection, UPDATE_EXHIBIT_AUDIENCE, audience.getId(), exhibit.getId());
    }

    @Override
    public void addExhibitAuthor(Exhibit exhibit, Author author) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) values(?, ?)";

        JdbcUtils.update(connection, ADD_EXHIBIT_AUTHOR, author.getId(), exhibit.getId());
    }

    @Override
    public void deleteExhibitAuthor(Exhibit exhibit, Author author) {
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

        Map<Audience, List<Exhibit>> groupedAudiences = new HashMap<>();
        AudienceRowMapper audienceRowMapper = new AudienceRowMapper();
        ExhibitRowMapper exhibitRowMapper = new ExhibitRowMapper();
        Exhibit exhibit;
        Audience audience;

        try (PreparedStatement statement = connection
                .prepareStatement(FIND_EXHIBIT_WITH_AUDIENCE)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    audience = audienceRowMapper.mapRow(resultSet);
                    exhibit = exhibitRowMapper.mapRow(resultSet);

                    if (groupedAudiences.containsKey(audience)) {
                        groupedAudiences.get(audience).add(exhibit);
                    } else {
                        groupedAudiences
                                .put(audience, new ArrayList<>(Collections.singletonList(exhibit)));
                    }
                }

                return groupedAudiences;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Exhibit objectToSave) {
        String SAVE_EXHIBIT = "INSERT INTO exhibits(type, material, techic, name) VALUES (?, ?, ?, ?)";

        JdbcUtils.update(connection, SAVE_EXHIBIT, objectToSave.getType().toString(), objectToSave.getMaterial(),
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
    public void update(Exhibit newObject) {
        String UPDATE_EXHIBIT = "UPDATE exhibits SET type = ?, material = ?, techic = ?, name = ? where id = ?";

        JdbcUtils.update(connection, UPDATE_EXHIBIT, newObject.getType().toString(), newObject.getMaterial(),
                newObject.getTechnique(), newObject.getName(), newObject.getId());
    }

    @Override
    public ExhibitStatistic findStatistic() {
        ExhibitStatistic statistic = new ExhibitStatistic();
        statistic.setMaterialStatistic(findStatisticByType(ExhibitType.SCULPTURE, "material"));
        statistic.setTechniqueStatistic(findStatisticByType(ExhibitType.PAINITNG, "techic"));

        return statistic;
    }

    private Map<String, Integer> findStatisticByType(ExhibitType type, String statisticField) {
        String STATISTIC_BY_MATERIAL =
                "SELECT " + statisticField + ", count(id) as number " +
                        "FROM exhibits " +
                        "WHERE type = ? " +
                        "GROUP BY " + statisticField;

        Map<String, Integer> statistic = new HashMap<>();
        String statisticFieldValue;

        try (PreparedStatement statement = connection.prepareStatement(STATISTIC_BY_MATERIAL)) {
            statement.setString(1, type.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    statisticFieldValue = resultSet.getString(statisticField);

                    statistic.put(statisticFieldValue, resultSet.getInt("number"));
                }

                return statistic;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
