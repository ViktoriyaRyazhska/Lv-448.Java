package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.entities.Country;
import inc.softserve.services.intefaces.VisaStatsService;

import java.util.*;

public class VisaStatsServiceImpl implements VisaStatsService {

    private final VisaDao visaDao;
    private final UsrDao usrDao;
    private final CountryDao countryDao;

    /**
     * Constructor with 3 parameters.
     */
    public VisaStatsServiceImpl(VisaDao visaDao, UsrDao usrDao, CountryDao countryDao) {
        this.visaDao = visaDao;
        this.usrDao = usrDao;
        this.countryDao = countryDao;
    }

    /**
     * Method returns number of visas from database by user
     *
     * @param email of user
     *
     * @return Number of visas
     */
    @Override
    public Optional<Integer> countVisasByUserEmail(String email) {
        return usrDao.findByEmail(email)
                .map(u -> visaDao.usrHasVisas(u.getEmail()));

    }

    /**
     * Method returns number of visas from database by country
     *
     * @param country name of country
     *
     * @return number visas by country
     */
    @Override
    public Optional<Integer> countVisasIssuedByCountry(String country) {
        return countryDao.findByCountryName(country)
                .map(c -> visaDao.issuedVisas(c.getCountry()));
    }

    /**
     * Method returns map with countries and number of visas issued for each country.
     *
     * @return map with countries and number of visas issued for each country.
     */
    public Map<String, Integer> countVisasIssuedByAllCountry() {
        Map<String, Integer> countrys = new HashMap<>();
        for (Country country : countryDao.findAll()) {
            Integer count = countVisasIssuedByCountry(country.getCountry()).orElseThrow();
            countrys.put(country.getCountry(), count);
        }
        return countrys;
    }


}
