package inc.softserve.dao.interfaces;

import inc.softserve.entities.City;

import java.util.Optional;
import java.util.Set;

public interface CityDao {

    Set<City> findAll();

    Optional<City> findById(Long cityId);

    Set<City> findByCountryName(String country);
}
