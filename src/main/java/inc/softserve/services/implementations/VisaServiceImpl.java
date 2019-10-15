package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.services.intefaces.VisaService;

import java.util.Optional;

public class VisaServiceImpl implements VisaService {

    private final VisaDao visaDao;
    private final UsrDao usrDao;
    private final CountryDao countryDao;

    public VisaServiceImpl(VisaDao visaDao, UsrDao usrDao, CountryDao countryDao) {
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
}
