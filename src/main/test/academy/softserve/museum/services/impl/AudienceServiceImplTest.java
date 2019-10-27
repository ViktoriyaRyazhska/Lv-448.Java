package academy.softserve.museum.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.AudienceService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class AudienceServiceImplTest {

    @Mock
    private AudienceDao mock;
    @InjectMocks
    private AudienceService service = new AudienceServiceImpl();


    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void saveSuccess() {
        Optional<Audience> audience = Optional.empty();
        when(mock.findByName(any())).thenReturn(audience);
        assertTrue(service.save(new Audience()));
    }

    @Test
    void saveFailure() {
        Optional<Audience> audience = Optional.of(new Audience());
        when(mock.findByName(any())).thenReturn(audience);
        assertThrows(NotSavedException.class, () -> service.save(new Audience()));
    }

    @Test
    void deleteByIdSuccess() {
        Optional<Audience> audience = Optional.of(new Audience());
        when(mock.findById(anyLong())).thenReturn(audience);
        assertTrue(service.deleteById(anyLong()));
    }

    @Test
    void deleteByIdFailure() {
        Optional<Audience> audience = Optional.empty();
        when(mock.findById(anyLong())).thenReturn(audience);
        assertThrows(NotDeletedException.class, () -> service.deleteById(anyLong()));
    }


    @Test
    void findByIdSuccess() {
        Optional<Audience> audience = Optional.of(new Audience());
        when(mock.findById(anyLong())).thenReturn(audience);
        assertEquals(service.findById(anyLong()), Optional.of(new Audience()));
    }

    @Test
    void findByIdFailure() {
        Optional<Audience> audience = Optional.empty();
        when(mock.findById(anyLong())).thenReturn(audience);
        assertThrows(NotFoundException.class, () -> service.findById(anyLong()));
    }

    @Test
    void findByNameSuccess() {
        Optional<Audience> audience = Optional.of(new Audience());
        when(mock.findByName(anyString())).thenReturn(audience);
        assertEquals(service.findByName(anyString()), Optional.of(new Audience()));
    }

    @Test
    void findByNameFailure() {
        Optional<Audience> audience = Optional.empty();
        when(mock.findByName(anyString())).thenReturn(audience);
        assertThrows(NotFoundException.class, () -> service.findByName(anyString()));
    }

    @Test
    void findAllSuccess() {
        List<Audience> audienceList = Arrays.asList(new Audience());
        when(mock.findAll()).thenReturn(audienceList);
        assertEquals(service.findAll(), audienceList);
    }

    @Test
    void findAllFailure() {
        List<Audience> audienceList = Collections.emptyList();
        when(mock.findAll()).thenReturn(audienceList);
        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void updateSuccess() {
        Optional<Audience> audience = Optional.of(new Audience("Audience"));
        when(mock.findByName(anyString())).thenReturn(audience);
        assertTrue(service.update(audience.get()));
    }

    @Test
    void updateFailure() {
        Optional<Audience> audience = Optional.of(new Audience());
        when(mock.findByName(anyString())).thenReturn(audience);
        assertThrows(NotUpdatedException.class, () -> service.update(audience.get()));
    }
}