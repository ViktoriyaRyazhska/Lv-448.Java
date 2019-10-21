package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.entities.City;
import inc.softserve.entities.Country;
import inc.softserve.entities.Hotel;
import inc.softserve.services.intefaces.HotelService;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class HotelServiceImpl implements HotelService {

    private final BookingDao bookingDao;
    private final HotelDao hotelDao;
    private final CityDao cityDao;
    private final CountryDao countryDao;

    public HotelServiceImpl(BookingDao bookingDao, HotelDao hotelDao, CityDao cityDao, CountryDao countryDao) {
        this.bookingDao = bookingDao;
        this.hotelDao = hotelDao;
        this.cityDao = cityDao;
        this.countryDao = countryDao;
    }

    /**
     *
     * @return all hotels without their bookings
     */
    @Override
    public Set<Hotel> findAll() {
        return hotelDao.findAll();
    }

    @Override
    public Set<Hotel> findHotelsByCityId(Long cityId, LocalDate fromDate) {
        return hotelDao.findHotelsByCityId(cityId)
                .stream()
                .peek(hotel -> hotel.setBookedRooms(
                        bookingDao.findBookingsByHotelIdAndDate(hotel.getId(), fromDate)
                ))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     *
     * @param countryName - a name of a country
     * @param cityName - a name of a city
     * @return - all hotels that are in the city
     */
    @Override
    public Set<Hotel> findHotelsByCountryAndCity(String countryName, final String cityName, final LocalDate fromDate){
        return countryDao.findByCountryName(countryName)
                .stream()
                .map(Country::getId)
                .flatMap(countryId ->
                        cityDao.findByCountryAndCity(countryId, cityName)
                                .stream()
                )
                .map(City::getId)
                .flatMap(cityId ->
                        hotelDao.findHotelsByCityId(cityId)
                                .stream()
                )
                .peek(hotel -> hotel.setBookedRooms(
                        bookingDao.findBookingsByHotelIdAndDate(hotel.getId(), fromDate)
                ))
                .collect(Collectors.toUnmodifiableSet());
    }
}
