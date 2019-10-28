package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.entities.stats.RoomStats;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.RoomStatsService;

import java.time.LocalDate;
import java.util.List;

public class RoomStatsServiceImpl implements RoomStatsService {

    private final RoomDao roomDao;

    /**
     * Constructor with 1 parameters.
     */
    public RoomStatsServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    /**
     * Method returned statistic rooms how booking
     *
     * @param hotelId id Hotel entity
     * @param startPeriod from date
     * @param endPeriod end date
     *
     * @exception InvalidTimePeriod when startPeriod > endPeriod
     * @return List statistic rooms how booking.
     */
    @Override
    public List<RoomStats> getRoomStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod){
        if (startPeriod.compareTo(endPeriod) > 0){
            throw new InvalidTimePeriod();
        }
        return roomDao.calcStats(hotelId, startPeriod, endPeriod);
    }
}
