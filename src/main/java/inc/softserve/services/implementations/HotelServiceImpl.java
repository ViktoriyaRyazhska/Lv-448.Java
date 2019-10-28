package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.*;
import inc.softserve.entities.City;
import inc.softserve.entities.Country;
import inc.softserve.entities.Hotel;
import inc.softserve.entities.Room;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.HotelService;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class HotelServiceImpl implements HotelService {

    private final BookingDao bookingDao;
    private final RoomDao roomDao;
    private final HotelDao hotelDao;
    private final CityDao cityDao;
    private final CountryDao countryDao;

    public HotelServiceImpl(BookingDao bookingDao, RoomDao roomDao, HotelDao hotelDao, CityDao cityDao, CountryDao countryDao) {
        this.bookingDao = bookingDao;
        this.roomDao = roomDao;
        this.hotelDao = hotelDao;
        this.cityDao = cityDao;
        this.countryDao = countryDao;
    }

    /**
     *
     * @param cityId - an identificator of a city
     * @param startPeriod - start of the booking period
     * @param endPeriod - end of the booking period
     * @return - all of the hotels that have at least one free room within given time interval.
     */
    @Override
    public Set<Hotel> findAvailableHotelsInCity(Long cityId, LocalDate startPeriod, LocalDate endPeriod){
        if (startPeriod.compareTo(endPeriod) > 0){
            throw new InvalidTimePeriod();
        }
        Set<Room> bookedRooms = roomDao.findBookedRoomsByCityIdAndTimePeriod(cityId, startPeriod, endPeriod);
        Set<Room> allRoomsInCity = roomDao.findByCityId(cityId);
        allRoomsInCity.removeAll(bookedRooms);
        return allRoomsInCity.stream()
                .map(Room::getHotel)
                .collect(Collectors.toSet());
    }

    /**
     *
     * @return all hotels without their bookings
     */
    @Override
    public Set<Hotel> findAll() {
        return hotelDao.findAll();
    }

    /**
     *
     * @param cityId - an identificator of a city
     * @return - hotels in the city
     */
    @Override
    public Set<Hotel> findHotelsByCityId(Long cityId) {
        return hotelDao.findHotelsByCityId(cityId)
                .stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     *
     * @param countryName - a name of a country
     * @param cityName - a name of a city
     * @return - all hotels that are in the city
     */
    @Override
    public Set<Hotel> findHotelsByCountryAndCity(String countryName, final String cityName){
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
                .collect(Collectors.toUnmodifiableSet());
    }
}
