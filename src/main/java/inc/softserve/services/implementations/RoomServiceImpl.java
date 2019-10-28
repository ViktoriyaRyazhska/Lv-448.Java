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

    /**
     * Constructor with 2 parameters.
     */
    public RoomServiceImpl(RoomDao roomDao, BookingDao bookingDao) {
        this.roomDao = roomDao;
        this.bookingDao = bookingDao;
    }

    /**
     * Method saves new user in database
     *
     * @param hotelId id Hotel entity
     * @param from from date
     *
     * @return Set all free room
     */
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
