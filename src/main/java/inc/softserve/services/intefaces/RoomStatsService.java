package inc.softserve.services.intefaces;

import inc.softserve.entities.stats.RoomStats;

import java.time.LocalDate;
import java.util.List;

public interface RoomStatsService {
    List<RoomStats> getRoomStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod);
}
