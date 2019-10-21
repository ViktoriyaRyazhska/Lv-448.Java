package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.entities.Country;
import inc.softserve.services.intefaces.CountryService;

import java.util.Set;

public class CountryServiceImpl implements CountryService {

    private final CountryDao countryDao;
    private final CityDao cityDao;

    public CountryServiceImpl(CountryDao countryDao, CityDao cityDao) {
        this.countryDao = countryDao;
        this.cityDao = cityDao;
    }

    @Override
    public Set<Country> findCountriesAndTheirCities() {
        Set<Country> countries = countryDao.findAll();
        countries.forEach(country -> country.setCities(
                cityDao.findByCountryId(country.getId())
        ));
        return countries;
    }
}
