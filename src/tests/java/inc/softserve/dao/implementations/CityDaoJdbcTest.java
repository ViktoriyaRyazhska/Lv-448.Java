package inc.softserve.dao.implementations;

import inc.softserve.dao.db_test_utils.InitDataBase;
import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.entities.City;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CityDaoJdbcTest {

    private static CountryDao countryDao;
    private static CityDao cityDao;
    private static Connection connection;

    @BeforeAll
    static void init() throws SQLException {
        connection = InitDataBase.createAndPopulate();
        countryDao = new CountryDaoJdbc(connection);
        cityDao = new CityDaoJdbc(connection, countryDao);
    }

    @AfterAll
    static void destroy() throws SQLException {
        connection.close();
    }

    @Test
    void findAll() {
        Set<String> citiesActual = cityDao.findAll().stream()
                .map(City::getCity)
                .collect(Collectors.toSet());
        Set<String> expected = Set.of("Lviv", "Kiev", "Milano", "Bologna", "Barcelona", "Las Vegas");
        assertEquals(expected, citiesActual);
    }
    
    @Test
    void findByCountryAndCity() {
        City actualCity = cityDao.findByCountryAndCity(2L, "Milano").orElseThrow();
        assertEquals(Long.valueOf(4), actualCity.getId());
    }
}