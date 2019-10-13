package inc.softserve.dao.interfaces;

import inc.softserve.entities.Country;

import java.util.List;
import java.util.Optional;

public interface CountryDao {

    List<Country> findAll();

    Optional<Country> findById(Long id);

    Optional<Country> findByCountryName(String country);
}
