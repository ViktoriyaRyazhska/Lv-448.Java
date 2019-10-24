package inc.softserve.dao.interfaces;

import inc.softserve.entities.Room;
import inc.softserve.entities.stats.RoomStats;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomDao extends Read<Room> {

    Set<Room> findAll();

    Optional<Room> findById(Long roomId);

    Set<Room> findByHotelId(Long hotelId);

    Set<Room> findRoomsByCityId(Long cityId);

    Set<Room> findAllFutureBookedRoomsByCityId(Long cityId, LocalDate from);

    List<RoomStats> calcStats(Long hotelId, LocalDate startPeriod, LocalDate endPeriod);
}
