package academy.softserve.museum.dao.impl.jdbc.mappers;

import academy.softserve.museum.dao.impl.jdbc.JdbcDaoTest;
import academy.softserve.museum.entities.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

class AuthorRowMapperTest {

    @Test
    void mapRowSuccess() throws SQLException {
        Author author = new Author(1, "Test", "Test");
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong("author_id")).thenReturn(author.getId());
        Mockito.when(resultSet.getString("author_first_name")).thenReturn(author.getFirstName());
        Mockito.when(resultSet.getString("author_last_name")).thenReturn(author.getLastName());

        JdbcDaoTest.assertAuthorEquals(author, new AuthorRowMapper().mapRow(resultSet));
    }

    @Test
    void mapRowFails() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        Mockito.when(resultSet.getLong(Mockito.anyString())).thenThrow(SQLException.class);
        Mockito.when(resultSet.getString(Mockito.anyString())).thenThrow(SQLException.class);

        Assertions.assertThrows(RuntimeException.class, () -> new AuthorRowMapper().mapRow(resultSet));
    }
}
