package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.CityDao;
import inc.softserve.dao.interfaces.CountryDao;
import inc.softserve.entities.City;
import inc.softserve.entities.Country;
import inc.softserve.services.intefaces.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CountryServiceImplTest {
    @Mock
    private CountryDao countryDao;
    @Mock
    private CityDao cityDao;

    CountryService countryService;

    @BeforeEach
    void init(){
        initMocks(this);
        countryService = new CountryServiceImpl(countryDao, cityDao);
    }

    @Test
    void findCountriesAndTheirCities() {
        when(countryDao.findAll()).thenReturn(new HashSet<Country>());
        when(cityDao.findByCountryId(1L)).thenReturn(new HashSet<City>());
        assertEquals(countryService.findCountriesAndTheirCities(),  new HashSet<Country>());
    }
}