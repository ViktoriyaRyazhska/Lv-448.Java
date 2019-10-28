package inc.softserve.dao.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.entities.Visa;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @Slf4j
public class VisaDaoJdbc implements VisaDao {

    private static final String TABLE_NAME = "travel_visas";

    private final Connection connection;
    private final UsrDao usrDao;
    private final CountryDao countryDao;

    public VisaDaoJdbc(Connection connection, UsrDao usrDao, CountryDao countryDao) {
        this.connection = connection;
        this.usrDao = usrDao;
        this.countryDao = countryDao;
    }

    @Override
    public Visa save(Visa visa){
        String query = "INSERT INTO travel_visas (visa_number, issued, expiration_date, country_id, usr_id) " +
                "VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement prepStat = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, visa.getVisaNumber());
            prepStat.setDate(2, Date.valueOf(visa.getIssued()));
            prepStat.setDate(3, Date.valueOf(visa.getExpirationDate()));
            prepStat.setLong(4, visa.getCountry().getId());
            prepStat.setLong(5, visa.getUsr().getId());
            prepStat.executeUpdate();
            try (ResultSet keys = prepStat.getGeneratedKeys()){
                if (keys.next()){
                    visa.setId(keys.getLong(1));
                }
            }
            return visa;
        } catch (SQLException e) {
          //  log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Eager implementation
     * @return - all visas
     */
    @Override
    public Set<Visa> findAll(){
        String query = "SELECT * FROM travel_visas";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            ResultSet resultSet = prepStat.executeQuery();
            return extractVisas(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
        //    log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Eager implementation
     * @param visaId - visa id
     * @return - Optional of visa if there is visa in the database with given id
     */
    @Override
    public Optional<Visa> findById(Long visaId){
        String query = "SELECT * FROM travel_visas WHERE id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, visaId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractVisas(resultSet).findAny();
        } catch (SQLException e) {
       //     log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param visaNumber - issued visa number
     * @return  Optional of visa if there is visa in the database with given visa number.
     */
    @Override
    public Optional<Visa> findByVisaNumber(String visaNumber){
        String query = "SELECT * FROM travel_visas WHERE visa_number = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setString(1, visaNumber);
            ResultSet resultSet = prepStat.executeQuery();
            return extractVisas(resultSet).findAny();
        } catch (SQLException e) {
         //   log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Eager implementation
     * @param countryId - countryId
     * @return - visas issued by a country
     */
    @Override
    public Set<Visa> findVisasByCountryId(Long countryId){
        String query = "SELECT * FROM travel_visas WHERE country_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, countryId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractVisas(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
          //  log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param usrId - an identificator of a user
     * @return - not empty set of visas that belong to a user if the user with given id exists and there are visas attached to him/her.
     */
    @Override
    public Set<Visa> findVisasByUserId(Long usrId){
        String query = "SELECT * FROM travel_visas WHERE usr_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, usrId);
            ResultSet resultSet = prepStat.executeQuery();
            return extractVisas(resultSet).collect(Collectors.toSet());
        } catch (SQLException e) {
        //    log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private Stream<Visa> extractVisas(ResultSet rs) throws SQLException {
        Stream.Builder<Visa> builder = Stream.builder();
        while (rs.next()){
            Visa visa = new Visa();
            visa.setId(rs.getLong("id"));
            visa.setVisaNumber(rs.getString("visa_number"));
            visa.setIssued(rs.getDate("issued").toLocalDate());
            visa.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
            visa.setCountry(countryDao
                    .findById(rs.getLong("country_id"))
                    .orElse(null));
            visa.setUsr(usrDao
                    .findById(rs.getLong("usr_id"))
                    .orElse(null));
            builder.accept(visa);
        }
        return builder.build();
    }

    /**
     *
     * @param country - country name
     * @return - number of visas issued by country. If the given country is not in the database then 0.
     */
    @Override
    public int issuedVisas(String country){
        String query = "SELECT COUNT(*) AS visa_count FROM countries " +
                "INNER JOIN travel_visas " +
                "ON countries.id = travel_visas.country_id " +
                "WHERE country = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setString(1, country);
            ResultSet resultSet = prepStat.executeQuery();
            return getCount(resultSet);
        } catch (SQLException e) {
          //  log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param countryId - country identificator
     * @return - number of visas issued by country. If the given country is not in the database then 0.
     */
    @Override
    public int issuedVisas(Long countryId){
        String query = "SELECT COUNT(id) AS visa_count FROM travel_visas WHERE country_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, countryId);
            ResultSet resultSet = prepStat.executeQuery();
            return getCount(resultSet);
        } catch (SQLException e) {
         //   log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param email - an email of a user.
     * @return - number of visas issued to the user. If there is no such an user then 0.
     */
    @Override
    public int usrHasVisas(String email){
        String query = "SELECT COUNT(*) AS visa_count FROM users " +
                "INNER JOIN travel_visas " +
                "ON users.id = travel_visas.usr_id " +
                "WHERE email = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setString(1, email);
            ResultSet resultSet = prepStat.executeQuery();
            return getCount(resultSet);
        } catch (SQLException e) {
         //   log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param usrId - an identificator of an user
     * @return - number of visas issued to the user. If there is no such an user then 0.
     */
    @Override
    public int usrHasVisas(Long usrId){
        String query = "SELECT COUNT(id) AS visa_count FROM travel_visas WHERE usr_id = ?";
        try (PreparedStatement prepStat = connection.prepareStatement(query)) {
            prepStat.setLong(1, usrId);
            ResultSet resultSet = prepStat.executeQuery();
            return getCount(resultSet);
        } catch (SQLException e) {
         //   log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private int getCount(ResultSet rs) throws SQLException {
        rs.next();
        return rs.getInt("visa_count");
    }
}
