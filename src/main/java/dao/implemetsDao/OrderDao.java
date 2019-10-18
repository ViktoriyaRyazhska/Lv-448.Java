package dao.implemetsDao;

import dao.interfaceDao.OrderDaoInterface;
import entities.Order;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class OrderDao implements OrderDaoInterface {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

//    @Override
//    public List<Order> findAll() {
//        String query = "SELECT * FROM orders";
//    }
//
//    @Override
//    public List<Order> findAllByUserId(Long userId) {
//        String query = "SELECT id_book_instance FROM orders where id_users=?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setLong(1, userId);
//            return extractOrders(preparedStatement.executeQuery()).collect(Collectors.toList());
//        } catch (SQLException e) {
//            log.error(e.getLocalizedMessage());
//            throw new RuntimeException();
//        }
//    }

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
    public void save(Order order) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Long id, Order order) {

    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return null;
    }
}
