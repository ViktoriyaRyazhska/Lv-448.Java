package inc.softserve.dao.interfaces;

import inc.softserve.entities.Hotel;
import inc.softserve.entities.stats.HotelStats;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HotelDao {

    Set<Hotel> findAll();

    Optional<Hotel> findById(Long hotelId);

    Set<Hotel> findHotelsByCityId(Long cityId);

    List<HotelStats> calcStats();
}
