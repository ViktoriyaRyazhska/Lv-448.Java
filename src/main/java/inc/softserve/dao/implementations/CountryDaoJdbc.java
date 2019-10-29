package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.entities.Country;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Slf4j
public class CountryDaoJdbc implements CountryDao {

    private static final String TABLE_NAME = "countries";

    private final Connection connection;

    public CountryDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    /**
     * Lazy implementation. All collections will not be brought automatically.
     * @return all countries that are present in the database.
     */
    @Override
    public Set<Country> findAll(){
        String query = "SELECT * FROM countries";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            ResultSet resultSet = prepStat.executeQuery();
            return extractCountries(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Lazy implementation. All collections will not be brought automatically.
     * Finds countries that are visited by a user
     * @param usrId - an identificator of an user
     * @return - not empty set if a user with given id exists and there are countries attached to him/her.
     */
    @Override
    public Set<Country> findCountriesVisitedByUsr(Long usrId){
        String query = "SELECT DISTINCT * FROM users " +
                "INNER JOIN usr_country " +
                "ON users.id = usr_country.usr_id " +
                "INNER JOIN countries " +
                "ON usr_country.country_id = countries.id " +
                "WHERE users.id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, usrId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractCountries(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Lazy implementation. All collections will not be brought automatically.
     * Maps result set to Country stream.
     * @param rs - result set that contains the date from countries table.
     * @return - stream of countries.
     */
    private Stream<Country> extractCountries(ResultSet rs) throws SQLException {
        Stream.Builder<Country> builder = Stream.builder();
        while (rs.next()){
            Country country = new Country();
            country.setId(rs.getLong("id"));
            country.setCountry(rs.getString("country"));
            builder.accept(country);
        }
        return builder.build();
    }

    /**
     * Lazy implementation. All collections will not be brought automatically.
     * @param id - an identificator of a country.
     * @return - not empty optional if a country with given id exists.
     */
    @Override
    public Optional<Country> findById(Long id){
        String query = "SELECT * FROM countries WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, id);
            ResultSet resultSet = prepStat.executeQuery();
            return extractCountries(resultSet).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Lazy implementation. All collections will not be brought automatically.
     * @param country - country name.
     * @return - not empty optional if a country with given name exists.
     */
    @Override
    public Optional<Country> findByCountryName(String country){
        String query = "SELECT * FROM countries WHERE country = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setString(1, country);
            ResultSet resultSet = prepStat.executeQuery();
            return extractCountries(resultSet).findAny();
        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
