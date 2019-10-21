package inc.softserve.services.intefaces;

import inc.softserve.dto.RoomDto;

import java.time.LocalDate;
import java.util.Set;

public interface RoomService {

    Set<RoomDto> findAvailableRooms(Long hotelId, LocalDate from);
}
