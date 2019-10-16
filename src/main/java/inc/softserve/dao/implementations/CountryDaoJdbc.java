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

    private final Connection connection;

    public CountryDaoJdbc(Connection connection) {
        this.connection = connection;
    }

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
