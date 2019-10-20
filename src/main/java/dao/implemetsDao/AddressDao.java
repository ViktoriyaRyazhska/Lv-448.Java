package dao.implemetsDao;

import dao.interfaceDao.AddressDaoInterface;
import entities.Address;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Stream;

public class AddressDao implements AddressDaoInterface {

    private Connection connection;

    public AddressDao(Connection connection) {
        this.connection = connection;
    }

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
