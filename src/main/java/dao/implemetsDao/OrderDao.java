package dao.implemetsDao;

import dao.interfaceDao.OrderDaoInterface;
import entities.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderDao implements OrderDaoInterface {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT * FROM orders";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractOrders(preparedStatement.executeQuery()).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(Order order) {
        String query = "INSERT INTO orders (date_order,id_user,id_book_instance) VALUE(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setLong(2,order.getUser().getId());
            preparedStatement.setLong(3,order.getBookInstance().getId());
            preparedStatement.executeUpdate();
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    order.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    @Override
//    public void save(Author author) {
//        String query = "INSERT INTO authors"
//                + "(id, first_name, last_name) "
//                + "VALUE (?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setLong(1, author.getId());
//            preparedStatement.setString(2, author.getAuthorFirstName());
//            preparedStatement.setString(3, author.getAuthorLastName());
//            preparedStatement.executeUpdate();
//            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
//                if (key.next()) {
//                    author.setId(key.getLong(1));
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private Stream<Order> extractOrders(ResultSet resultSet) throws SQLException {
        Stream.Builder<Order> orders = Stream.builder();
        while (resultSet.next()) {
            orders.add(Order.builder()
                    .id(resultSet.getLong("id"))
                    .dateOrder(resultSet.getDate("date_order").toLocalDate())
                    .dateReturn(resultSet.getDate("date_return").toLocalDate())
                    .build()
            );
        }
        resultSet.close();
        return orders.build();
    }

    @Override
    public Optional<Order> findById(Long id) {
        String query = "SELECT * FROM orders where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractOrders(resultSet).findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Order order) {
        String query = "UPDATE * FROM orders where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    private Stream<Order> extractOrders(ResultSet resultSet) throws SQLException {
//        Stream.Builder<Order> builder = Stream.builder();
//        while (resultSet.next()) {
//            builder.add(
//                    Order.builder()
//                            .id(resultSet.getLong("id"))
//                            .dateOrder(resultSet.getDate("date_order"))
//                            .dateReturn(Optional.ofNullable(resultSet.getDate("date_return").toLocalDate()).orElse(null))
//                            .build()
//            );
//        }
//        return builder.build();
//    }


    @Override
    public List<Order> findAllByUserId(Long userId) {
        return null;
    }
}
