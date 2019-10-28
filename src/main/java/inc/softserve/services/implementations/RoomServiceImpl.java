package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dto.RoomDto;
import inc.softserve.services.intefaces.RoomService;
import inc.softserve.utils.mappers.RoomToRoomDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {

    private final RoomDao roomDao;
    private final BookingDao bookingDao;

    public RoomServiceImpl(RoomDao roomDao, BookingDao bookingDao) {
        this.roomDao = roomDao;
        this.bookingDao = bookingDao;
    }

    @Override
    public Set<RoomDto> findRoomsAndTheirBookingsStartingFrom(Long hotelId, final LocalDate from){
        return roomDao.findByHotelId(hotelId)
                .stream()
                .peek(room -> room.setBooking(
                        bookingDao.findBookingsByRoomIdAndDate(room.getId(), from)
                ))
                .map(RoomToRoomDto::map)
                .collect(Collectors.toSet());
    }
}
