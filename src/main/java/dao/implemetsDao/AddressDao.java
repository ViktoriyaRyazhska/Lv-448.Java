package dao.implemetsDao;

import dao.interfaceDao.AddressDaoInterface;
import entities.Address;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Stream;

public class AddressDao implements AddressDaoInterface {

    /**
     * The connection field used for interaction with database.
     */
    private Connection connection;
    /**
     * The addressDao field used for implementing Singleton.
     */
    private static AddressDao addressDao;

    /**
     * Constructor, which creates an instance of the class using connection.
     *
     * @param connection used for interaction with database.
     */
    private AddressDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method for getting an instance of AddressDao class.
     *
     * @param connection used for interaction with database.
     * @return an instance of AddressDao class.
     */
    public static AddressDao getInstance(Connection connection) {
        if (addressDao == null) {
            addressDao = new AddressDao(connection);
        }

        return addressDao;
    }

    /**
     * Method which saves objects in database.
     *
     * @param address object which must be saved.
     */
    @Override
    public void save(Address address) {
        String query = "INSERT INTO addresses (city, street, building_number, apartment) VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getStreet());
            preparedStatement.setLong(3, address.getBuildingNumber());
            preparedStatement.setLong(4, address.getApartment());
            preparedStatement.executeUpdate();
            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                if (key.next()) {
                    address.setId(key.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for finding an Author by its id.
     *
     * @param id address id.
     * @return Author object wrapped in Optional.
     *
     * In case of absence an object with such id
     * method returns Optional.empty().
     */
    @Override
    public Optional<Address> findById(Long id) {
        String query = "SELECT * FROM addresses where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractAddresses(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Address> extractAddresses(ResultSet resultSet) throws SQLException {
        Stream.Builder<Address> authors = Stream.builder();
        while (resultSet.next()) {
            authors.add(Address.builder()
                    .id(resultSet.getLong("id"))
                    .city(resultSet.getString("city"))
                    .street(resultSet.getString("street"))
                    .apartment(resultSet.getLong("building_number"))
                    .buildingNumber(resultSet.getLong("apartment"))
                    .build());
        }
        resultSet.close();
        return authors.build();

    }

    /**
     * Method used for updating objects in the database.
     *
     * @param address object to update.
     */
    @Override
    public void update(Address address) {
        String query = "UPDATE addresses SET city = ?, street = ?, building_number = ?, apartment = ?  WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getStreet());
            preparedStatement.setLong(3, address.getBuildingNumber());
            preparedStatement.setLong(4, address.getApartment());
            preparedStatement.setLong(5, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
