package academy.softserve.museum.dao.impl.jdbc;

import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.dao.impl.jdbc.mappers.AudienceRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.AuthorRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExcursionRowMapper;
import academy.softserve.museum.dao.impl.jdbc.mappers.ExhibitRowMapper;
import academy.softserve.museum.entities.*;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;

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
        String FIND_EXHIBIT_BY_NAME = "SELECT * FROM exhibits WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(FIND_EXHIBIT_BY_NAME)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new ExhibitRowMapper().mapRow(resultSet));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exhibit> findByAuthor(Author author) {
        String FIND_EXHIBITS_BY_AUTHOR_ID =
                "SELECT e.id, e.type, e.material, e.techic, e.name " +
                        "FROM exhibits as e " +
                        "INNER JOIN autor_exhibit as ae " +
                        "ON ae.exhibit_id = e.id " +
                        "WHERE ae.autor_id = ?";

        List<Exhibit> exhibits = new ArrayList<>();
        ExhibitRowMapper rowMapper = new ExhibitRowMapper();

        try (PreparedStatement statement = connection
                .prepareStatement(FIND_EXHIBITS_BY_AUTHOR_ID)) {
            statement.setLong(1, author.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    exhibits.add(rowMapper.mapRow(resultSet));
                }

                return exhibits;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exhibit> findByEmployee(Employee employee) {
        String FIND_EXHIBITS_BY_EMPLOYEE_ID =
                "SELECT e.id, e.type, e.material, e.techic, e.name " +
                        "FROM exhibits as e " +
                        "INNER JOIN employees AS em " +
                        "ON e.audience_id = em.audience_id " +
                        "WHERE em.id = ?";

        List<Exhibit> exhibits = new ArrayList<>();
        ExhibitRowMapper rowMapper = new ExhibitRowMapper();

        try (PreparedStatement statement = connection
                .prepareStatement(FIND_EXHIBITS_BY_EMPLOYEE_ID)) {
            statement.setLong(1, employee.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    exhibits.add(rowMapper.mapRow(resultSet));
                }

                return exhibits;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> findAuthorsByExhibit(Exhibit exhibit) {
        String FIND_AUTHORS_BY_EXHIBIT_ID =
                "SELECT a.id, a.first_name, a.last_name " +
                        "FROM autors AS a " +
                        "INNER JOIN autor_exhibit AS ae " +
                        "ON a.id = ae.autor_id " +
                        "WHERE ae.exhibit_id = ? ";

        List<Author> authors = new ArrayList<>();
        AuthorRowMapper rowMapper = new AuthorRowMapper();

        try (PreparedStatement statement = connection
                .prepareStatement(FIND_AUTHORS_BY_EXHIBIT_ID)) {
            statement.setLong(1, exhibit.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    authors.add(rowMapper.mapRow(resultSet));
                }

                return authors;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Audience findAudienceByExhibit(Exhibit exhibit) {
        String FIND_AUDIENCE_BY_EXHIBIT_ID = "SELECT id as audience_id, name as audience_name" +
                " FROM audiences WHERE id = ?";

        try (PreparedStatement statement = connection
                .prepareStatement(FIND_AUDIENCE_BY_EXHIBIT_ID)) {
            statement.setLong(1, exhibit.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new AudienceRowMapper().mapRow(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateExhibitAudience(Exhibit exhibit, Audience audience) {
        String UPDATE_EXHIBIT_AUDIENCE = "UPDATE exhibit SET audience_id = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXHIBIT_AUDIENCE)) {
            statement.setLong(1, audience.getId());
            statement.setLong(2, exhibit.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addExhibitAuthor(Exhibit exhibit, Author author) {
        String ADD_EXHIBIT_AUTHOR = "INSERT INTO autor_exhibit(autor_id, exhibit_id) values(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(ADD_EXHIBIT_AUTHOR)) {
            statement.setLong(1, author.getId());
            statement.setLong(2, exhibit.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteExhibitAuthor(Exhibit exhibit, Author author) {
        String DELETE_EXHIBIT_AUTHOR = "DELETE FROM autor_exhibit WHERE autor_id = ? and exhibit_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_EXHIBIT_AUTHOR)) {
            statement.setLong(1, author.getId());
            statement.setLong(2, exhibit.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Audience, List<Exhibit>> findAllGroupedByAudience() {
        String FIND_EXHIBIT_WITH_AUDIENCE =
                "SELECT e.id, e.type, e.material, e.techic, a.id, e.name as audience_id, a.name as audience_name " +
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

        try (PreparedStatement statement = connection.prepareStatement(SAVE_EXHIBIT)) {
            statement.setString(1, objectToSave.getType().toString());
            statement.setString(2, objectToSave.getMaterial());
            statement.setString(3, objectToSave.getTechnique());
            statement.setString(4, objectToSave.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String DELETE_EXHIBIT_BY_ID = "DELETE FROM exhibits WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE_EXHIBIT_BY_ID)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Exhibit> findById(long id) {
        String FIND_EXHIBIT_BY_ID = "SELECT * FROM exhibits WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(FIND_EXHIBIT_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new ExhibitRowMapper().mapRow(resultSet));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Exhibit> findAll() {
        String FIND_ALL_EXHIBITS = "SELECT * FROM exhibits";
        List<Exhibit> exhibits = new ArrayList<>();
        ExhibitRowMapper rowMapper = new ExhibitRowMapper();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_EXHIBITS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    exhibits.add(rowMapper.mapRow(resultSet));
                }
            }

            return exhibits;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Exhibit newObject) {
        String UPDATE_EXHIBIT = "UPDATE exhibits SET type = ?, material = ?, techic = ?, name = ? where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXHIBIT)) {
            statement.setString(1, newObject.getType().toString());
            statement.setString(2, newObject.getMaterial());
            statement.setString(3, newObject.getTechnique());
            statement.setString(4, newObject.getName());
            statement.setLong(5, newObject.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
