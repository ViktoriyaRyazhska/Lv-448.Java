package academy.softserve.museum.services.impl;

import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.entities.*;
import academy.softserve.museum.entities.dto.ExhibitDto;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.ExhibitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

class ExhibitServiceImplTest {

    @Mock
    ExhibitDao exhibitDaoMock;

    @Mock
    AuthorDao authorDaoMock;

    @Mock
    AudienceDao audienceDaoMock;

    @InjectMocks
    ExhibitService exhibitService = new ExhibitServiceImpl();
    private ExhibitDto testExhibitDto;
    private Exhibit testExhibit;
    private Employee testEmployee;

    @BeforeEach
    void init() {
        initMocks(this);

        testExhibitDto = new ExhibitDto(ExhibitType.PAINTING,
                "Name",
                null,
                "Technique", 1L, new ArrayList<Long>());

        testExhibit = new Exhibit(1L, ExhibitType.PAINTING, "Material", "Technique", "Name");

        testEmployee = new Employee(1L, "Test", "Test",
                EmployeePosition.MANAGER, "Test", "Test");
    }

    @Test
    void save() {
        when(exhibitDaoMock.findByName("Name")).thenReturn(Optional.ofNullable(testExhibit));
        assertThrows(NotSavedException.class, () -> exhibitService.save(testExhibitDto));
    }

    @Test
    void deleteByIdSuccessTest() {
        long id = 1;

        when(exhibitDaoMock.findById(id)).thenReturn(Optional.of(testExhibit));

        assertTrue(exhibitService.deleteById(id));
    }

    @Test
    void deleteByIdFailureTest() {
        Optional<Exhibit> exhibit = Optional.empty();
        when(exhibitDaoMock.findById(anyLong())).thenReturn(exhibit);
        assertThrows(NotDeletedException.class, () -> exhibitService.deleteById(anyLong()));
    }

    @Test
    void findByIdSuccessTest() {
        long id = 1L;

        when(exhibitDaoMock.findById(id)).thenReturn(Optional.of(testExhibit));
        when(exhibitDaoMock.loadForeignFields(testExhibit)).thenReturn(testExhibit);

        assertEquals(Optional.of(testExhibit), exhibitService.findById(id));
    }

    @Test
    void findByIdFailureTest() {
        long id = 1L;

        when(exhibitDaoMock.findById(id)).thenReturn(Optional.empty());
        when(exhibitDaoMock.loadForeignFields(testExhibit)).thenReturn();

        assertThrows(NotFoundException.class, () -> exhibitService.findById(id));
    }

    @Test
    void findByNameSuccessTest() {
        Optional<Exhibit> exhibit = Optional.of(testExhibit);
        when(exhibitDaoMock.findByName(any())).thenReturn(Optional.ofNullable(testExhibit));
        when(exhibitDaoMock.loadForeignFields(exhibit.get())).thenReturn(exhibit.get());
        assertEquals(testExhibit, exhibitService.findByName(exhibit.get().getName()).get());
    }

    @Test
    void findByNameFailureTest() {
        Optional<Exhibit> exhibit = Optional.of(new Exhibit());
        when(exhibitDaoMock.findByName(any())).thenReturn(exhibit);
        when(exhibitDaoMock.loadForeignFields(exhibit.get())).thenReturn(exhibit.get());
        assertEquals(exhibit, exhibitService.findByName(exhibit.get().getName()));
    }

    @Test
    void findAllSuccessTest() {
        Exhibit exhibit = new Exhibit(1L, ExhibitType.PAINTING, "Material", "Technique", "Name");
        List<Exhibit> exhibits = Arrays.asList(exhibit, exhibit);
        when(exhibitDaoMock.loadForeignFields(exhibitDaoMock.findAll())).thenReturn(exhibits);
        assertEquals(2, exhibitService.findAll().size());
    }

    @Test
    void findAllFailureTest() {
        List<Exhibit> exhibits = Collections.emptyList();
        when(exhibitDaoMock.loadForeignFields(exhibitDaoMock.findAll())).thenReturn(exhibits);
        assertThrows(NotFoundException.class, () -> exhibitService.findAll());
    }

    @Test
    void updateSuccessTest() {
        Exhibit exhibit = new Exhibit(1L, ExhibitType.PAINTING, "Material", "Technique", "Name");
        when(exhibitDaoMock.findByName(exhibit.getName())).thenReturn(Optional.of(exhibit));
        assertTrue(exhibitService.update(exhibit));
    }

    @Test
    void updateFailureTest() {
        Optional<Exhibit> exhibits =
                Optional.of(new Exhibit(1L, ExhibitType.PAINTING, "Material", "Technique", "Name"));
        when(exhibitDaoMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotUpdatedException.class, () -> exhibitService.update(exhibits.get()));
    }

    @Test
    void findByAuthorSuccessTest() {
        Exhibit exhibit = new Exhibit(1L, ExhibitType.PAINTING, "Material", "Technique", "Name");
        List<Exhibit> exhibits = Arrays.asList(exhibit, exhibit);
        when(exhibitDaoMock.loadForeignFields(exhibitDaoMock.findByAuthor(any()))).thenReturn(exhibits);
        assertEquals(exhibits, exhibitService.findByAuthor(any()));
    }

    @Test
    void findByAuthorFailureTest() {
        List<Exhibit> exhibits = Collections.emptyList();
        when(exhibitDaoMock.loadForeignFields(exhibitDaoMock.findByAuthor(any()))).thenReturn(exhibits);
        assertThrows(NotFoundException.class, ()-> exhibitService.findByAuthor(any()));
    }

    @Test
    void findByEmployeeSuccessTest() {
        List<Exhibit> exhibits = Arrays.asList(testExhibit, testExhibit);
        when(exhibitDaoMock.loadForeignFields(exhibitDaoMock.findByEmployee(any()))).thenReturn(exhibits);
        assertEquals(exhibits, exhibitService.findByEmployee(testEmployee));
    }

    @Test
    void findByEmployeeFailureTest() {
        List<Exhibit> exhibits = Collections.emptyList();
        when(exhibitDaoMock.loadForeignFields(exhibitDaoMock.findByEmployee(any()))).thenReturn(exhibits);
        assertThrows(NotFoundException.class, ()-> exhibitService.findByEmployee(any()));
    }

    @Test
    void updateExhibitAudienceSuccessTest() {
        Audience audience = new Audience(1, "Test");

        when(exhibitDaoMock.findById(testExhibit.getId())).thenReturn(Optional.of(testExhibit));
        when(audienceDaoMock.findById(audience.getId())).thenReturn(Optional.of(audience));

        assertTrue(exhibitService.updateExhibitAudience(testExhibit, audience));
    }

    @Test
    void updateExhibitAudienceFailureTest() {
        Audience audience = new Audience(1, "Test");

        when(exhibitDaoMock.findById(testExhibit.getId())).thenReturn(Optional.empty());
        when(audienceDaoMock.findById(audience.getId())).thenReturn(Optional.of(audience));

        assertThrows(NotUpdatedException.class, () -> exhibitService.updateExhibitAudience(testExhibit, audience));
    }

    @Test
    void addExhibitAuthorSuccessTest() {
        Author author = new Author(1, "Firstname", "Lastname");

        when(exhibitDaoMock.findById(testExhibit.getId())).thenReturn(Optional.of(testExhibit));
        when(authorDaoMock.findById(author.getId())).thenReturn(Optional.of(author));

        assertTrue(exhibitService.addExhibitAuthor(testExhibit, author));
    }

    @Test
    void addExhibitAuthorFailureTest() {
        Author author = new Author(1, "Firstname", "Lastname");

        when(exhibitDaoMock.findById(testExhibit.getId())).thenReturn(Optional.empty());
        when(authorDaoMock.findById(author.getId())).thenReturn(Optional.of(author));

        assertThrows(NotSavedException.class, () -> exhibitService.addExhibitAuthor(testExhibit, author));
    }

    @Test
    void deleteExhibitAuthorSuccessTest() {
        Author author = new Author(1, "Firstname", "Lastname");

        when(exhibitDaoMock.findById(1)).thenReturn(Optional.of(testExhibit));
        when(authorDaoMock.findById(author.getId())).thenReturn(Optional.of(author));

        assertTrue(exhibitService.deleteExhibitAuthor(testExhibit, author));
    }

    @Test
    void deleteExhibitAuthorFailureTest() {
        Author author = new Author(1, "Firstname", "Lastname");

        when(exhibitDaoMock.findById(1)).thenReturn(Optional.empty());
        when(authorDaoMock.findById(author.getId())).thenReturn(Optional.of(author));

        assertThrows(NotDeletedException.class, () -> exhibitService.deleteExhibitAuthor(testExhibit, author));
    }

    @Test
    void findAllGroupedByAudienceFailureTest() {
        when(exhibitDaoMock.findAllGroupedByAudience()).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> exhibitService.findAllGroupedByAudience());
    }


    @Test
    void findStatisticSuccessTest() {
        ExhibitStatistic statistics = new ExhibitStatistic();
        when(exhibitDaoMock.findStatistic()).thenReturn(statistics);
        assertEquals(statistics, exhibitService.findStatistic());
    }

    @Test
    void findStatisticFailureTest() {
        when(exhibitDaoMock.findStatistic()).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> exhibitService.findStatistic());
    }

    @Test
    void getTypes() {
        assertEquals(exhibitService.getTypes(), Arrays.asList(ExhibitType.values()));
    }


    @Test
    void findByAudienceFailureTest() {
        when(exhibitDaoMock.loadForeignFields(exhibitDaoMock.findByAudience(any()))).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> exhibitService.findAll());
    }
}