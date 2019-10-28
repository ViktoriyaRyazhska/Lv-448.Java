package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.entities.stats.HotelStats;
import inc.softserve.entities.stats.RoomStats;
import inc.softserve.services.intefaces.HotelStatsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class HotelStatsServiceImpl implements HotelStatsService {

    private final HotelDao hotelDao;
    private final RoomDao roomDao;

    /**
     * Constructor with 2 parameters.
     */
    public HotelStatsServiceImpl(HotelDao hotelDao, RoomDao roomDao) {
        this.hotelDao = hotelDao;
        this.roomDao = roomDao;
    }
    /**
     * Method saves new user in database
     *
     * @return statistics of the hotels (number of visitors, average time of booking).
     */
    @Override
    public List<HotelStats> calcHotelStats() {
        return hotelDao.calcStats();
    }
}
