package inc.softserve.services.intefaces;

import inc.softserve.entities.Country;

import java.util.Set;

public interface CountryService {

    Set<Country> findCountriesAndTheirCities();
}
