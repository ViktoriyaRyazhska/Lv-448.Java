package dao.implemetsDao;

import dao.interfaceDao.BookInstanceDaoInterface;
import dao.interfaceDao.OrderDaoInterface;
import dao.interfaceDao.UserDaoInterface;
import entities.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderDao implements OrderDaoInterface {

    /**
     * The field used for interaction with database.
     */
    private final Connection connection;

    /**
     * The field used for loading and setting a user for an order.
     */
    private UserDaoInterface userDao;
    /**
     * The field used for loading and setting a book instance for an order.
     */
    private BookInstanceDaoInterface bookInstanceDao;

    /**
     * The field used for implementing Singleton.
     */
    private static OrderDao orderDao;

    /**
     * Constructor, which creates an instance of the class using connection.
     *
     * @param connection used for interaction with database.
     */
    public OrderDao(Connection connection, UserDaoInterface userDao, BookInstanceDaoInterface bookInstanceDao) {
        this.connection = connection;
        this.userDao = userDao;
        this.bookInstanceDao = bookInstanceDao;
    }

    /**
     * Method for getting an instance of OrderDao class.
     *
     * @param connection used for interaction with database.
     * @return an instance of OrderDao class.
     */
    public static OrderDao getInstance(Connection connection, UserDaoInterface userDao, BookInstanceDaoInterface bookInstanceDao) {
        if (orderDao == null) {
            orderDao = new OrderDao(connection, userDao, bookInstanceDao);
        }

        return orderDao;
    }

    /**
     * Method which saves objects in database.
     *
     * @param order object which must be saved.
     */
    @Override
    public void save(Order order) {
        String query = "INSERT INTO orders (date_order,id_users,id_book_instance) VALUE(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setLong(2, order.getUser().getId());
            preparedStatement.setLong(3, order.getBookInstance().getId());
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

    /**
     * Method used for finding all users from the database.
     *
     * @return list of orders objects from database.
     */
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

    private Stream<Order> extractOrders(ResultSet resultSet) throws SQLException {
        Stream.Builder<Order> orders = Stream.builder();

        while (resultSet.next()) {
            Optional<Date> date = Optional.ofNullable(resultSet.getDate("date_return"));
            orders.add(Order.builder()
                    .id(resultSet.getLong("id"))
                    .dateOrder(resultSet.getDate("date_order").toLocalDate())
                    .dateReturn(date.map(Date::toLocalDate).orElse(null))
                    .user(userDao.findById(resultSet.getLong("id_users")).orElseThrow(RuntimeException::new))
                    .bookInstance(bookInstanceDao.findById(resultSet.getLong("id_book_instance")).orElseThrow(RuntimeException::new))
                    .build()
            );
        }
        resultSet.close();
        return orders.build();
    }

    /**
     * Method used for finding an Order by its id.
     *
     * @param id order's id.
     * @return Order object wrapped in Optional.
     *
     * In case of absence an object with such id
     * method returns Optional.empty().
     */
    @Override
    public Optional<Order> findById(Long id) {
        String query = "SELECT * FROM orders where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<Order> any = extractOrders(resultSet).findAny();
            return any;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for closing an order by updating
     * return date
     *
     * @param id order's id in which need to update return date
     * @param dateReturn return date
     */
    @Override
    public void updateReturnDate(Long id, LocalDate dateReturn) {
        String query = "UPDATE orders SET date_return = ? where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(dateReturn));
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used for finding all orders by user
     *
     * @param userId user's id
     * @return list of orders by some user
     */
    @Override
    public List<Order> findAllByUserId(Long userId) {
        String query = "SELECT * FROM orders where id_users=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractOrders(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Method used for finding all orders by
     * user's phone number
     *
     * @param userPhoneNumber user's phone number
     * @return list of orders by user phone number
     */
    @Override
    public List<Order> findAllByUserPhoneNumber(String userPhoneNumber) {
        String query = "SELECT * FROM orders LEFT JOIN users" +
                " ON orders.id_users = users.id WHERE phone_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userPhoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractOrders(resultSet).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
