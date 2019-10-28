package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.HotelDao;
import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.entities.stats.HotelStats;
import inc.softserve.services.intefaces.HotelStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class HotelStatsServiceImplTest {
    @Mock
    private HotelDao hotelDao;
    @Mock
    private RoomDao roomDao;


    private HotelStatsService hotelStatsService;

    @BeforeEach
    void init () {
        initMocks(this);
        hotelStatsService = new HotelStatsServiceImpl(hotelDao, roomDao);
    }

    @Test
    void calcHotelStatsTest() {
        when(hotelDao.calcStats()).thenReturn(new ArrayList<>());
        assertEquals(hotelStatsService.calcHotelStats(), new ArrayList<HotelStats>());
    }
}