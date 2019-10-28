package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.*;
import inc.softserve.entities.*;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class HotelServiceImplTest {
    @Mock
    private BookingDao bookingDao;
    @Mock
    private HotelDao hotelDao;
    @Mock
    private CityDao cityDao;
    @Mock
    private CountryDao countryDao;
    @Mock
    private RoomDao roomDao;

    private HotelService hotelService;
    private DateTimeFormatter formatter;


    @BeforeEach
    void init () {
        initMocks(this);
        hotelService = new HotelServiceImpl(bookingDao, roomDao, hotelDao, cityDao, countryDao);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    void findAll() {
        when(hotelDao.findAll()).thenReturn(new HashSet<>());
        assertEquals(hotelService.findAll(), new HashSet<>());
    }

    @Test
    void findHotelsByCityIdTest() {
        when(hotelDao.findHotelsByCityId(1L)).thenReturn(new HashSet<Hotel>());
        assertEquals(hotelService.findHotelsByCityId(1L), new HashSet<Hotel>());
    }

}