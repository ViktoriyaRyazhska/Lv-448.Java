package inc.softserve.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UsrCrudJdbsTest {

    @Mock
    Connection connection;

    private UsrCrudJdbs usrCrudJdbs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usrCrudJdbs = new UsrCrudJdbs(connection);
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }
}