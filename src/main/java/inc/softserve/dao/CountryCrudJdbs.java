package inc.softserve.dao;

import inc.softserve.entities.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryCrudJdbs implements Crud<Country> {

    private Connection connection;

    public CountryCrudJdbs(Connection connectDb) {
        this.connection = connectDb;
    }

    @Override
    public int save(Country country) {
        String query = "INSERT INTO countries (id, country) VALUES (?, ?)";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, country.getId());
            prepStat.setString(2, country.getCountry());
            return prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Country country) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM Countries WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, id);
            return prepStat.executeUpdate();
        } catch (SQLException e) {
            //  TODO  Add log
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Country> findAll() {
        String query = "SELECT * FROM Country";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            return extractCountries(prepStat.executeQuery());
        } catch (SQLException e) {
            //  TODO  Add log
            throw new RuntimeException(e);
        }
    }

    private List<Country> extractCountries (ResultSet resSet) throws SQLException {
        List<Country> countiesList = new ArrayList<>();
        while (resSet.next()) {
            Country countries = new Country();
            countries.setId(resSet.getLong("id"));
            countries.setCountry(resSet.getString("country"));
        }
    return countiesList;
    }

    @Override
    public Optional<Country> findById(Long id) {
        String query = "SELECT * FROM Country WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, id);
            return extractCountry(prepStat.executeQuery());
        } catch (SQLException e) {
            //  TODO  Add log
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Country> findByUniqueField(String unique) {
        return null;
    }

    private Optional<Country> extractCountry(ResultSet resSet) throws SQLException {
        Country country = null;
        while (resSet.next()) {
            country = new Country();
            country.setId(resSet.getLong("id"));
            country.setCountry(resSet.getString("country"));
        }
        return Optional.ofNullable(country);
    }
}