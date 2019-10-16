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

    public HotelStatsServiceImpl(HotelDao hotelDao, RoomDao roomDao) {
        this.hotelDao = hotelDao;
        this.roomDao = roomDao;
    }

    @Override
    public List<HotelStats> calcHotelStats() {
        return hotelDao.calcStats();
    }

    @Override
    public Set<RoomStats> calcRoomStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod){
        return roomDao.calcStats(hotelId, startPeriod, endPeriod);
    }
}
