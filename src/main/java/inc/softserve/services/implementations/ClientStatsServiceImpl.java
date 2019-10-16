package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.entities.Usr;
import inc.softserve.services.intefaces.ClientStatsService;

import java.util.Optional;

public class ClientStatsServiceImpl implements ClientStatsService {

    private final UsrDao usrDao;
    private final CountryDao countryDao;
    private final VisaDao visaDao;
    private final BookingDao bookingDao;

    public ClientStatsServiceImpl(UsrDao usrDao, CountryDao countryDao, VisaDao visaDao, BookingDao bookingDao) {
        this.usrDao = usrDao;
        this.countryDao = countryDao;
        this.visaDao = visaDao;
        this.bookingDao = bookingDao;
    }

    @Override
    public Optional<Usr> getUser(String email) {
        Optional<Usr> usr = usrDao.findByEmail(email);
        usr.ifPresent(u -> {
            u.setVisas(visaDao
                    .findVisasByUserId(u.getId()));
            u.setVisitedCountries(countryDao
                    .findCountriesVisitedByUsr(u.getId()));
            u.setBookings(bookingDao
                    .findBookingsByUsrId(u.getId()));
        });
        return usr;
    }
}
