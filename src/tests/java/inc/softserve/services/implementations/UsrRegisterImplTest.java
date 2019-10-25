package inc.softserve.services.implementations;

import inc.softserve.dao.interfaces.*;
import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;
import inc.softserve.security.JavaNativeSaltGen;
import inc.softserve.services.intefaces.UsrRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UsrRegisterImplTest {

    @Mock
    static BookingDao bookingDao;
    @Mock
    static RoomDao roomDao;
    @Mock
    static HotelDao hotelDao;
    @Mock
    static CityDao cityDao;
    @Mock
    static CountryDao countryDao;
    @Mock
    static UsrDao usrDao;
    @Mock
    static VisaDao visaDao;
    @Mock
    static Connection connection;
    @Mock
    static UsrDto usrDto;
    @Mock
    static VisaDto visaDto;

    static UsrRegisterService usrRegisterService;

    @BeforeEach
    void init() {
        initMocks(this);
        usrRegisterService = new UsrRegisterImpl(new JavaNativeSaltGen(), usrDao, visaDao, countryDao, connection);
        usrDto = new UsrDto("igor@gmail.com", "+380964504812", "hash","Test",
                "Test","2010-10-10");
        visaDto = new VisaDto("123", LocalDate.of(2014, 10,10), LocalDate.of(2015, 10,10), "USA");
    }

    @Test
    void registerIncorrectDateTest() {
        Map<String, String> map = new HashMap<>();
        map.put("date", "Date is not valid!");
        usrDto = new UsrDto("igor@gmail.com", "+3804504812", "hash","Test",
                "Test","2010/10/10");
        when(usrDao.findByEmail(anyString())).thenReturn(Optional.empty());
        assertEquals(usrRegisterService.register(usrDto, visaDto), map);
    }

    @Test
    void registerIncorrectPhoneTest() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "Given phone number is not valid!");
        usrDto = new UsrDto("igor@gmail.com", "+380ssd4812", "hash","Test",
                "Test","2010-10-10");
        when(usrDao.findByEmail(anyString())).thenReturn(Optional.empty());
        assertEquals(usrRegisterService.register(usrDto, visaDto), map);
    }

    @Test
    void registerUserIsRegisteredTest() {
        Map<String, String> map = new HashMap<>();
        map.put("empty", "An account with such an email already exists!");
        usrDto = new UsrDto("igor@gmail.com", "+380964504812", "hash","Test",
                "Test","2010-10-10");
        when(usrDao.findByEmail("igor@gmail.com")).thenReturn(Optional.of(new Usr()));
        assertEquals(usrRegisterService.register(usrDto, visaDto), map);
    }

    @Test
    void registerIncorrectEmailTest() {
        Map<String, String> map = new HashMap<>();
        map.put("email", "Given email is not valid!");
        usrDto = new UsrDto("igorgmail.com", "+380964504812", "hash","Test",
                "Test","2010-10-10");
        assertEquals(usrRegisterService.register(usrDto, visaDto), map);
    }

    @Test
    void loginInIncorrectPasswordTest() {
        when(usrDao.findByEmail("igor@gmail.com")).thenReturn(Optional.of(new Usr()));
        assertThrows(RuntimeException.class, () -> usrRegisterService.login("igor@gmail.com", "hash"));
    }

    @Test
    void loginIncorrectEmailTest() {
        when(usrDao.findByEmail("s1@gmail.com")).thenReturn(Optional.ofNullable(null));
        assertThrows(NullPointerException.class, () -> usrRegisterService.login("s1gmail.com", "hash"));
    }

    @Test
    void validateInvalidDataTest() {
        Map<String, String> actual = usrRegisterService.validateData("", "");
        Map<String, String> expected = new HashMap<>();
        expected.put("email", "Given email is not valid!");
        assertEquals(expected, actual);
    }

    @Test
    void validateDataTest() {
        Map<String, String> actual = usrRegisterService.validateData("igor@gmail.com", "1234");
        Map<String, String> expected = new HashMap<>();
        assertEquals(expected, actual);
    }

    @Test
    void validateInvalidEmailTest() {
        Map<String, String> actual = usrRegisterService.validateData("igormail.com", "1234");
        Map<String, String> expected = new HashMap<>();
        expected.put("email", "Given email is not valid!");
        assertEquals(expected, actual);
    }

    @Test
    void initUsrDto() {
        assertEquals(usrDto, usrRegisterService.initUsrDto("Test", "Test", "igor@gmail.com","+380964504812",
                "2010-10-10","hash"));
    }

    @Test
    void initVisaDto() {
        assertEquals(visaDto, usrRegisterService.initVisaDto("USA", "2015-10-10", "2014-10-10", "123")  );

    }
}