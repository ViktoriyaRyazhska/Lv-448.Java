package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.entities.City;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @Slf4j
public class CityDaoJdbc implements CityDao {

    private static final String TABLE_NAME = "cities";

    private final Connection connection;
    private final CountryDao countryDao;

    public CityDaoJdbc(Connection connection, CountryDao countryDao) {
        this.connection = connection;
        this.countryDao = countryDao;
    }

    /**
     * Lazy implementation
     * @return all cities
     */
    @Override
    public Set<City> findAll() {
        String query = "SELECT * FROM cities";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            ResultSet resultSet = prepStat.executeQuery();
            return extractCities(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
         //   log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Stream<City> extractCities(ResultSet rs) throws SQLException {
        Stream.Builder<City> builder = Stream.builder();
        while (rs.next()){
            City city = new City();
            city.setId(rs.getLong("id"));
            city.setCity(rs.getString("city"));
            city.setCountry(countryDao
                    .findById(rs.getLong("country_id"))
                    .orElseThrow(() ->  new RuntimeException("There is a city that is not connected with a country")));
            builder.accept(city);
        }
        return builder.build();
    }

    /**
     *
     * @param cityId - an identificator of a city.
     * @return - not empty optional if there is a city with given id.
     */
    @Override
    public Optional<City> findById(Long cityId) {
        String query = "SELECT * FROM cities WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, cityId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractCities(resultSet).findAny();
        } catch (SQLException e) {
          //  log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param countryId - an identificator of a country.
     * @return - not empty set if a country with given id exists in the database and there are cities attached to it.
     */
    @Override
    public Set<City> findByCountryId(Long countryId) {
        String query = "SELECT * FROM cities WHERE country_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, countryId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractCities(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
         //   log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param country - country name
     * @return - not empty set if a country with given name exists in the database and there are cities attached to it.
     */
    @Override
    public Set<City> findByCountryName(String country){
        String query = "SELECT * FROM cities " +
                "INNER JOIN countries " +
                "ON cities.country_id  = countries.id " +
                "WHERE country = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setString(1, country);
            ResultSet resultSet = prepStat.executeQuery();
            return extractCities(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param countryId - an identificator of a country
     * @param city - name of a city
     * @return - not empty optional if city with given name and country id exists in the database.
     */
    @Override
    public Optional<City> findByCountryAndCity(Long countryId, String city){
        String query = "SELECT * FROM cities " +
                "WHERE country_id = ? AND city = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, countryId);
            prepStat.setString(2, city);
            ResultSet resultSet = prepStat.executeQuery();
            return extractCities(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
