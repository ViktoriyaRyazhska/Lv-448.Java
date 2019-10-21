package inc.softserve.dao.interfaces;

import inc.softserve.entities.Country;

import java.util.Optional;
import java.util.Set;

public interface CountryDao extends Read<Country> {

    Set<Country> findAll();

    Optional<Country> findById(Long id);

    Set<Country> findCountriesVisitedByUsr(Long usrId);

    Optional<Country> findByCountryName(String country);
}
