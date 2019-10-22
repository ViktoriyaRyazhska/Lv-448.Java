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

    public VisaStatsServiceImpl(VisaDao visaDao, UsrDao usrDao, CountryDao countryDao) {
        this.visaDao = visaDao;
        this.usrDao = usrDao;
        this.countryDao = countryDao;
    }

    @Override
    public Optional<Integer> countVisasByUserEmail(String email) {
        return usrDao.findByEmail(email)
                .map(u -> visaDao.usrHasVisas(u.getEmail()));

    }

    @Override
    public Optional<Integer> countVisasIssuedByCountry(String country) {
        return countryDao.findByCountryName(country)
                .map(c -> visaDao.issuedVisas(c.getCountry()));
    }

    public Map<String, Integer> countVisasIssuedByAllCountry() {
        Map<String, Integer> countrys = new HashMap<>();
        for (Country country : countryDao.findAll()) {
            Integer count = countVisasIssuedByCountry(country.getCountry()).orElseThrow();
            countrys.put(country.getCountry(), count);
        }
        return countrys;
    }


}
