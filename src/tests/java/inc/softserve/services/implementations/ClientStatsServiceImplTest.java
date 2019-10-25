package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.*;
import inc.softserve.entities.Usr;
import inc.softserve.services.intefaces.ClientStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClientStatsServiceImplTest {

    @Mock
    private BookingDao bookingDao;
    @Mock
    private CountryDao countryDao;
    @Mock
    private UsrDao usrDao;
    @Mock
    private ClientStatsService clientStatsService;
    @Mock
    private VisaDao visaDao;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        clientStatsService = new ClientStatsServiceImpl(usrDao, countryDao, visaDao, bookingDao);
    }

    @Test
    void getUserByEmailTest1() {
        Usr user = new Usr();
        user.setEmail("igor@gmail.com");
        when(usrDao.findByEmail("igor@gmail.com")).thenReturn(Optional.of(user));
        assertEquals(clientStatsService.getUser("igor@gmail.com"), Optional.of(user));
    }

    @Test
    void getUserByInvalidEmailTest1() {
        when(usrDao.findByEmail("igorgmail.com")).thenReturn(Optional.of(new Usr()));
        assertThrows(NullPointerException.class, () -> clientStatsService.getUser("igogmail.com"));
    }
}