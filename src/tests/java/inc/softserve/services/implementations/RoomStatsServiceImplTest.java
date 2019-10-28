package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.RoomDao;
import inc.softserve.entities.stats.RoomStats;
import inc.softserve.exceptions.InvalidTimePeriod;
import inc.softserve.services.intefaces.RoomStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class RoomStatsServiceImplTest {

    @Mock
    RoomDao roomDao;

    RoomStatsService roomStatsService;
    DateTimeFormatter formatter;

    @BeforeEach
    void init(){
        initMocks(this);
        roomStatsService = new RoomStatsServiceImpl(roomDao);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    void getRoomStatsTest() {
        when(roomDao.calcStats(1L, LocalDate.parse("2019-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter))).thenReturn(new ArrayList<RoomStats>());
        assertEquals(roomStatsService.getRoomStats(1L, LocalDate.parse("2019-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter)), new ArrayList<RoomStats>());
    }

    @Test
    void getRoomStatsIncorectDateTest() {
        when(roomDao.calcStats(1L, LocalDate.parse("2019-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter))).thenThrow(InvalidTimePeriod.class);
        assertThrows(InvalidTimePeriod.class, () -> roomStatsService.getRoomStats(1L, LocalDate.parse("2019-10-10", formatter),
                LocalDate.parse("2020-10-10", formatter)));
    }

}