package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.*;
import inc.softserve.dto.on_request.BookingReqDto;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BookingServiceImplTest {

    @Mock
    private UsrCountryDao usrCountryDao;
    @Mock
    private BookingDao bookingDao;
    @Mock
    private UsrDao usrDao;
    @Mock
    private HotelDao hotelDao;
    @Mock
    private RoomDao roomDao;

    private BookingService bookingService;
    private DateTimeFormatter formatter;

    @BeforeEach
    void init () {
        initMocks(this);
        bookingService = new BookingServiceImpl(bookingDao, usrDao, hotelDao, roomDao, usrCountryDao);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    @Test
    void bookTest() {
        BookingReqDto bookingReqDto = new BookingReqDto(LocalDate.of(2022, 10, 10),
                LocalDate.of(2021, 10, 10), 1L, 1L, 1L);
        when(bookingDao.save(anyObject())).thenThrow(InvalidTimePeriod.class);
        assertThrows(InvalidTimePeriod.class, () -> bookingService.book(bookingReqDto, LocalDateTime
                .of(2021, 10, 10, 0, 0, 0)));
    }
}