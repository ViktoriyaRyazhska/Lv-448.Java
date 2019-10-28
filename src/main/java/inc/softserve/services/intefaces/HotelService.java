package inc.softserve.services.intefaces;

import inc.softserve.entities.Hotel;

import java.time.LocalDate;
import java.util.Set;

public interface HotelService {

    Set<Hotel> findAvailableHotelsInCity(Long cityId, LocalDate startPeriod, LocalDate endPeriod);

    /**
     *
     * @return all hotels in the database
     */
    Set<Hotel> findAll();

    /**
     *
     * @param cityId - an identificator of a city
     * @return not empty set if a city with given id exists and there are hotels attached to the city.
     */
    Set<Hotel> findHotelsByCityId(Long cityId);

    /**
     *
     * @param countryName - name of a country
     * @param cityName - name of a city
     * @return not empty set if a country and city exists and there are hotels attached to them.
     */
    Set<Hotel> findHotelsByCountryAndCity(String countryName, String cityName);
}
