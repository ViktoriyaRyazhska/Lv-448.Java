package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.entities.Country;
import inc.softserve.services.intefaces.CountryService;

import java.util.Set;

public class CountryServiceImpl implements CountryService {

    private final CountryDao countryDao;

    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public Set<Country> findAll() {
        return countryDao.findAll();
    }
}
