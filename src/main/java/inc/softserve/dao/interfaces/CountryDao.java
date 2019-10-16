package inc.softserve.dao.interfaces;

import inc.softserve.entities.Country;

import java.util.Optional;
import java.util.Set;

public interface CountryDao {

    Set<Country> findAll();

    Set<Country> findCountriesVisitedByUsr(Long usrId);

    Optional<Country> findById(Long id);

    Optional<Country> findByCountryName(String country);
}
