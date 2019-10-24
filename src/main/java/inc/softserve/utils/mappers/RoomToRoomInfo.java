package inc.softserve.utils.mappers;

import inc.softserve.dto.RoomDto;
import inc.softserve.entities.Room;

import java.util.stream.Collectors;

public class RoomToRoomInfo {

    public static RoomDto map(Room room){
        return RoomDto.builder()
                .id(room.getId())
                .chamberNumber(room.getChamberNumber())
                .bedrooms(room.getBedrooms())
                .luxury(room.getLuxury())
                .city(room.getCity())
                .hotel(room.getHotel())
                .bookings(room.getBooking().stream()
                        .map(BookingToBookingResp::map)
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
