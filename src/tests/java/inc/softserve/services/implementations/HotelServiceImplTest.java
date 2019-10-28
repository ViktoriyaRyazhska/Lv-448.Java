package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.BookingDao;
import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.HotelDao;
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

    private HotelService hotelService;
    private DateTimeFormatter formatter;


    @BeforeEach
    void init () {
        initMocks(this);
        hotelService = new HotelServiceImpl(bookingDao, hotelDao, cityDao, countryDao);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    void findAvailableHotelsInCity() {
        when(hotelDao.findHotelsByCityIdAndPeriod(1L, LocalDate.parse("2019-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter))).thenReturn(new HashSet<Hotel>());
        assertEquals(hotelService.findAvailableHotelsInCity(1L, LocalDate.parse("2019-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter)), new HashSet<Hotel>());
    }

    @Test
    void findAvailableHotelsInCityIncorrectDateTest() {
        when(hotelDao.findHotelsByCityIdAndPeriod(1L, LocalDate.parse("2021-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter))).thenThrow(InvalidTimePeriod.class);
        assertThrows(InvalidTimePeriod.class,() -> hotelService.findAvailableHotelsInCity(1L, LocalDate.parse("2021-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter)));
    }

    @Test
    void findAll() {
        when(hotelDao.findAll()).thenReturn(new HashSet<>());
        assertEquals(hotelService.findAll(), new HashSet<>());
    }

    @Test
    void findHotelsByCityIdTest() {
        when(hotelDao.findHotelsByCityId(1L)).thenReturn(new HashSet<Hotel>());
        assertEquals(hotelService.findHotelsByCityId(1L, LocalDate.now()), new HashSet<Hotel>());
    }

}