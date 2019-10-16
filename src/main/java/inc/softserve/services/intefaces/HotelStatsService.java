package inc.softserve.services.intefaces;

import inc.softserve.entities.stats.HotelStats;
import inc.softserve.entities.stats.RoomStats;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface HotelStatsService {

    List<HotelStats> calcHotelStats();

    Set<RoomStats> calcRoomStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod);
}
