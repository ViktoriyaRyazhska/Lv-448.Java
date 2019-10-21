package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dto.RoomDto;
import inc.softserve.services.intefaces.RoomService;

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
    public Set<RoomDto> findAvailableRooms(Long hotelId, final LocalDate from) {
        return bookingDao.findBookingsByHotelIdAndDate(hotelId, from)
                .stream()
                .map(booking -> RoomDto.builder()
                        .room(booking.getRoom())
                        .bookedTo(booking.getCheckin())
                        .bookedFrom(booking.getCheckout())
                        .build()
                )
                .collect(Collectors.toSet());
    }
}
