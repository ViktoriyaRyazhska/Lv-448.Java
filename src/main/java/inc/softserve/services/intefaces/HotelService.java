package inc.softserve.services.intefaces;

import inc.softserve.entities.Hotel;

import java.time.LocalDate;
import java.util.Set;

public interface HotelService {

    Set<Hotel> findAll();

    Set<Hotel> findHotelsByCityId(Long cityId, LocalDate fromDate);

    Set<Hotel> findHotelsByCountryAndCity(String countryName, String cityName, LocalDate fromDate);
}
