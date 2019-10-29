package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.dao.interfaces.UsrDao;
import inc.softserve.dao.interfaces.VisaDao;
import inc.softserve.entities.Country;
import inc.softserve.entities.Usr;
import inc.softserve.services.intefaces.VisaStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class VisaStatsServiceImplTest {
    @Mock
    VisaDao visaDao;

    @Mock
    UsrDao usrDao;

    @Mock
    CountryDao countryDao;

    VisaStatsService visaStatsService;

    @BeforeEach
    void setUp(){
        initMocks(this);
        visaStatsService = new VisaStatsServiceImpl(visaDao, usrDao, countryDao);
    }

    @Test
    void countVisasByUserEmail() {
        when(usrDao.findByEmail("r@gmail.com")).thenReturn(Optional.of(new Usr()));
        when(visaDao.usrHasVisas("r@gmail.com")).thenReturn(0);
        assertEquals(visaStatsService.countVisasByUserEmail("r@gmail.com"), Optional.of(0));
    }

    @Test
    void countVisasByUserEmailIncorrect() {
        when(usrDao.findByEmail("rgmail.com")).thenReturn(Optional.of(new Usr()));
        assertThrows(RuntimeException.class, () -> visaStatsService.countVisasByUserEmail(""));
    }

    @Test
    void countVisasIssuedByCountryTest() {
        when(countryDao.findByCountryName("USA")).thenReturn(Optional.of(new Country()));
        when(visaDao.issuedVisas("USA")).thenReturn(0);
        assertEquals(visaStatsService.countVisasIssuedByCountry("USA"), Optional.of(0));
    }

    @Test
    void countVisasIssuedByIncorrectCountryTest() {
        when(countryDao.findByCountryName("USA")).thenThrow(RuntimeException.class);
        when(visaDao.issuedVisas("USA")).thenReturn(anyInt());
        assertThrows(RuntimeException.class, () -> visaStatsService.countVisasIssuedByCountry("USA"));
    }

    @Test
    void countVisasIssuedByAllCountry() {
        when(countryDao.findAll()).thenReturn(new HashSet<Country>());
        when(countryDao.findByCountryName("USA")).thenReturn(Optional.of(new Country()));
        assertEquals(visaStatsService.countVisasIssuedByAllCountry(), new HashMap<String, Integer>());
    }

}