package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.entities.stats.RoomStats;
import inc.softserve.services.intefaces.RoomStatsService;

import java.time.LocalDate;
import java.util.List;

public class RoomStatsServiceImpl implements RoomStatsService {

    private final RoomDao roomDao;

    public RoomStatsServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public List<RoomStats> getRoomStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod){
        return roomDao.calcStats(hotelId, startPeriod, endPeriod);
    }
}
