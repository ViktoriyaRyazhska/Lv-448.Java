package inc.softserve.dao.interfaces;

import inc.softserve.entities.City;

import java.util.Optional;
import java.util.Set;

public interface CityDao extends Read<City> {

    Set<City> findAll();

    Optional<City> findById(Long cityId);

    Set<City> findByCountryId(Long countryId);

    Set<City> findByCountryName(String country);

    Optional<City> findByCountryAndCity(Long countryId, String city);
}
