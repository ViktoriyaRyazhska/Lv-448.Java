package academy.softserve.museum.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.entities.Author;
import academy.softserve.museum.entities.Exhibit;
import academy.softserve.museum.entities.ExhibitType;
import academy.softserve.museum.entities.dto.AuthorDto;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.AuthorService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorMock;
    @Mock
    private ExhibitDao exhibitMock;
    @InjectMocks
    private AuthorService service = new AuthorServiceImpl();

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void addExhibitAuthorSuccess() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        Optional<Exhibit> exhibit = Optional.of(new Exhibit
                (ExhibitType.SCULPTURE, "first", "last", "name"));
        when(authorMock.findById(anyLong())).thenReturn(Optional.empty());
        when(exhibitMock.findById(anyLong())).thenReturn(Optional.empty());
        assertTrue(service.addExhibitAuthor(author.get(), exhibit.get()));
    }

    @Test
    void addExhibitAuthorFailure() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        Optional<Exhibit> exhibit = Optional.of(new Exhibit
                (ExhibitType.SCULPTURE, "first", "last", "name"));
        when(authorMock.findById(anyLong())).thenReturn(author);
        when(exhibitMock.findById(anyLong())).thenReturn(exhibit);
        assertThrows(NotSavedException.class,
                () -> service.addExhibitAuthor(author.get(), exhibit.get()));
    }

    @Test
    void deleteExhibitAuthorSuccess() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        when(authorMock.findById(anyLong())).thenReturn(author);
        assertTrue(service.deleteById(anyLong()));
    }

    @Test
    void deleteExhibitAuthorFailure() {
        when(authorMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotDeletedException.class, () -> service.deleteById(anyLong()));
    }

    @Test
    void saveSuccess() {
        Optional<Author> author = Optional.empty();
        when(authorMock.findByFullName(anyString(), anyString())).thenReturn(author);
        assertTrue(service.save(new AuthorDto(anyString(), anyString())));
    }

    @Test
    void saveFailure() {
        Optional<Author> author = Optional.of(new Author());
        when(authorMock.findByFullName(anyString(), anyString())).thenReturn(author);
        assertThrows(NotSavedException.class,
                () -> service.save(new AuthorDto(anyString(), anyString())));
    }

    @Test
    void deleteByIdSuccess() {
        Optional<Author> author = Optional.of(new Author());
        when(authorMock.findById(anyLong())).thenReturn(author);
        assertTrue(service.deleteById(anyLong()));
    }

    @Test
    void deleteByIdFailure() {
        Optional<Author> author = Optional.empty();
        when(authorMock.findById(anyLong())).thenReturn(author);
        assertThrows(NotDeletedException.class, () -> service.deleteById(anyLong()));
    }

    @Test
    void findByIdSuccess() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        when(authorMock.findById(anyLong())).thenReturn(author);
        when(authorMock.loadForeignFields(author.get())).thenReturn(author.get());
        assertEquals(service.findById(anyLong()), author);
    }

    @Test
    void findByIdFailure() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        when(authorMock.findById(anyLong())).thenReturn(author);
        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void findByFullNameSuccess() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        when(authorMock.findByFullName(anyString(), anyString())).thenReturn(author);
        when(authorMock.loadForeignFields(author.get())).thenReturn(author.get());
        assertEquals(service.findByFullName(anyString(), anyString()), author);
    }

    @Test
    void findByFullNameFailure() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        when(authorMock.findByFullName(anyString(), anyString())).thenReturn(author);
        when(authorMock.loadForeignFields(author.get())).thenReturn(author.get());
        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void findAllSuccess() {
        List<Author> authorList = Arrays.asList(new Author("first name", "last name"));
        when(authorMock.findAll()).thenReturn(authorList);
        when(authorMock.loadForeignFields(authorList)).thenReturn(authorList);
        assertEquals(service.findAll(), authorList);
    }

    @Test
    void findAllFailure() {
        List<Author> authorList = Collections.emptyList();
        when(authorMock.findAll()).thenReturn(authorList);
        assertThrows(NotFoundException.class, () -> service.findAll());
    }

    @Test
    void updateSuccess() {
        Optional<Author> author = Optional.of(new Author("first name", "last name"));
        when(authorMock.findByFullName(anyString(), anyString())).thenReturn(author);
        assertTrue(service.update(author.get()));
    }

    @Test
    void updateFailure() {
        Optional<Author> author = Optional.of(new Author());
        when(authorMock.findByFullName(anyString(), anyString())).thenReturn(author);
        assertThrows(NotUpdatedException.class, () -> service.update(author.get()));
    }
}