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
    /**
     * Constructor with 1 parameters.
     */
    public CountryDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method if return all countries with database.
     *
     * @return Set all  country with database
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
     * Method find all countries which visited by user.
     *
     * @param usrId id user.
     *
     * @return all countries which visited user
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
     * Method ResultSet convert to country entity.
     *
     * @param rs sql Result set.
     *
     * @return Country entity
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
