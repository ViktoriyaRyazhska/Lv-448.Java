package academy.softserve.museum.utils;

import academy.softserve.museum.dao.impl.jdbc.mappers.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUtils {

    public static <T> Optional<T> queryForObject(Connection connection, String query, RowMapper<T> mapper, Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            insertParameters(statement, parameters);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.mapRow(resultSet));
                }

                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> query(Connection connection, String query, RowMapper<T> mapper, Object... parameters) {
        List<T> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            insertParameters(statement, parameters);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(mapper.mapRow(resultSet));
                }

                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int update(Connection connection, String query, Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            insertParameters(statement, parameters);

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertParameters(PreparedStatement statement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Integer) {
                    statement.setInt(i, (Integer) parameters[i]);
                } else if (parameters[i] instanceof Long) {
                    statement.setLong(i, (Long) parameters[i]);
                } else if (parameters[i] instanceof String) {
                    statement.setString(i, (String) parameters[i]);
                } else if (parameters[i] instanceof Date) {
                    statement.setDate(i, (Date) parameters[i]);
                } else {
                    throw new RuntimeException("There are no mapping for " + parameters[i].getClass());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
