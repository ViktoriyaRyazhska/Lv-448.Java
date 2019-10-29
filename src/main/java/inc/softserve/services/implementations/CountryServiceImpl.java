package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.entities.Country;
import inc.softserve.services.intefaces.CountryService;

import java.util.Set;

public class CountryServiceImpl implements CountryService {

    private final CountryDao countryDao;
    private final CityDao cityDao;

    /**
     * Constructor with 2 parameters.
     */
    public CountryServiceImpl(CountryDao countryDao, CityDao cityDao) {
        this.countryDao = countryDao;
        this.cityDao = cityDao;
    }

    /**
     * Method returned all countries and their cities.
     *
     * @return Map all countries and their cities..
     */
    @Override
    public Set<Country> findCountriesAndTheirCities() {
        Set<Country> countries = countryDao.findAll();
        countries.forEach(country -> country.setCities(
                cityDao.findByCountryId(country.getId())
        ));
        return countries;
    }
}
