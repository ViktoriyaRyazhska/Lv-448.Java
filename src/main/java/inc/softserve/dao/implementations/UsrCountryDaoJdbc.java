package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.UsrCountryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsrCountryDaoJdbc implements UsrCountryDao {

    private final Connection connection;

    public UsrCountryDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts date into the intermediate table usr_country
     * @param usrId - an identificator of a user
     * @param countryId - an identificator of a country
     */
    @Override
    public void usrVisitedCountry(Long usrId, Long countryId) {
        String query = "INSERT INTO usr_country (usr_id, country_id) VALUES (?, ?)";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, usrId);
            prepStat.setLong(2, countryId);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
