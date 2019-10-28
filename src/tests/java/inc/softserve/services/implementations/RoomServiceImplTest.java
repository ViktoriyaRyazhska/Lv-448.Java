package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.dto.RoomDto;
import inc.softserve.entities.Booking;
import inc.softserve.services.intefaces.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;


class RoomServiceImplTest {
    @Mock
    private RoomDao roomDao;
    @Mock
    private BookingDao bookingDao;

    private RoomService roomService;
    private DateTimeFormatter formatter;


    @BeforeEach
    void init(){
        initMocks(this);
        roomService = new RoomServiceImpl(roomDao, bookingDao);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    void findRoomsAndTheirBookingsStartingFromTest() {
        when(roomDao.findByHotelId(anyLong())).thenReturn(new HashSet<>());
        when(bookingDao.findBookingsByRoomIdAndDate(1L, LocalDate.now())).thenReturn(new HashSet<Booking>());
        assertEquals(roomService.findRoomsAndTheirBookingsStartingFrom(1L,LocalDate.now()), new HashSet<RoomDto>());
    }
}