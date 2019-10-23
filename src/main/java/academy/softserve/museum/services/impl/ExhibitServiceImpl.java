package academy.softserve.museum.services.impl;

import academy.softserve.museum.constant.ErrorMessage;
import academy.softserve.museum.dao.AudienceDao;
import academy.softserve.museum.dao.AuthorDao;
import academy.softserve.museum.dao.ExhibitDao;
import academy.softserve.museum.database.DaoFactory;
import academy.softserve.museum.entities.*;
import academy.softserve.museum.entities.dto.ExhibitDto;
import academy.softserve.museum.entities.statistic.ExhibitStatistic;
import academy.softserve.museum.exception.NotDeletedException;
import academy.softserve.museum.exception.NotFoundException;
import academy.softserve.museum.exception.NotSavedException;
import academy.softserve.museum.exception.NotUpdatedException;
import academy.softserve.museum.services.ExhibitService;

import java.util.*;

public class ExhibitServiceImpl implements ExhibitService {

    private final ExhibitDao exhibitDao;
    private final AuthorDao authorDao;
    private final AudienceDao audienceDao;

    public ExhibitServiceImpl() {
        exhibitDao = DaoFactory.exhibitDao();
        authorDao = DaoFactory.authorDao();
        audienceDao = DaoFactory.audienceDao();
    }

    public ExhibitServiceImpl(ExhibitDao exhibitDao, AuthorDao authorDao,
            AudienceDao audienceDao) {
        this.exhibitDao = exhibitDao;
        this.authorDao = authorDao;
        this.audienceDao = audienceDao;
    }

    @Override
    public boolean save(ExhibitDto dto) {
        if (exhibitDao.findByName(dto.getName()).isPresent()) {
            throw new NotSavedException(ErrorMessage.EXHIBIT_NOT_SAVED);
        } else {
            Exhibit exhibit = new Exhibit(
                    dto.getType(),
                    dto.getMaterial(),
                    dto.getTechnique(),
                    dto.getName());

            exhibitDao.save(exhibit);
            Long exhibitId = exhibitDao.findByName(exhibit.getName()).get().getId();
            exhibit.setId(exhibitId);

            Audience audience = new Audience();
            audience.setId(dto.getAudienceId());

            exhibitDao.updateAudience(exhibit, audience);

            for (Long id : dto.getAuthorsId()) {
                Author author = new Author();
                author.setId(id);
                exhibitDao.addAuthor(exhibit, author);
            }
            return true;
        }
    }

    @Override
    public boolean deleteById(long id) {
        if (exhibitDao.findById(id).isPresent()) {
            exhibitDao.deleteById(id);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EXHIBIT_NOT_DELETED);
        }
    }

    @Override
    public Optional<Exhibit> findById(long id) {
        Exhibit exhibit = exhibitDao.findById(id).orElse(null);
        return Optional.of(Optional.of(exhibitDao.loadForeignFields(exhibit))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public Optional<Exhibit> findByName(String name) {
        Exhibit exhibit = exhibitDao.findByName(name).orElse(null);
        return Optional.of(Optional.of(exhibitDao.loadForeignFields(exhibit))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND)));
    }

    @Override
    public List<Exhibit> findAll() {
        List<Exhibit> exhibitList = exhibitDao.loadForeignFields(exhibitDao.findAll());
        if(exhibitList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }

    @Override
    public boolean update(Exhibit newObject) {
        if (exhibitDao.findByName(newObject.getName()).isPresent()) {
            exhibitDao.update(newObject);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EXHIBIT_NOT_UPDATED);
        }
    }

    @Override
    public List<Exhibit> findByAuthor(Author author) {
        List<Exhibit> exhibitList = exhibitDao.loadForeignFields(exhibitDao.findByAuthor(author));
        if(exhibitList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }

    @Override
    public List<Exhibit> findByEmployee(Employee employee) {
        List<Exhibit> exhibitList = exhibitDao.loadForeignFields(exhibitDao.findByEmployee(employee));
        if(exhibitList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }

    @Override
    public boolean updateExhibitAudience(Exhibit exhibit, Audience audience) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (audienceDao.findById(audience.getId()).isPresent())) {
            exhibitDao.updateAudience(exhibit, audience);
            return true;
        } else {
            throw new NotUpdatedException(ErrorMessage.EXHIBIT_AUDIENCE_NOT_UPDATED);
        }
    }

    @Override
    public boolean addExhibitAuthor(Exhibit exhibit, Author author) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (authorDao.findById(author.getId()).isPresent())) {
            throw new NotSavedException(ErrorMessage.EXHIBIT_AUTHOR_NOT_SAVED);
        } else {
            exhibitDao.addAuthor(exhibit, author);
            return true;
        }
    }

    @Override
    public boolean deleteExhibitAuthor(Exhibit exhibit, Author author) {
        if ((exhibitDao.findById(exhibit.getId()).isPresent()) &&
                (authorDao.findById(author.getId()).isPresent())) {
            exhibitDao.deleteAuthor(exhibit, author);
            return true;
        } else {
            throw new NotDeletedException(ErrorMessage.EXHIBIT_NOT_DELETED);
        }
    }

    @Override
    public Map<Audience, List<Exhibit>> findAllGroupedByAudience() {
        Map<Audience, List<Exhibit>> map = exhibitDao.findAllGroupedByAudience();
        if(map.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return map;
    }

    @Override
    public ExhibitStatistic findStatistic() {
        try {
            return exhibitDao.findStatistic();
        } catch (Exception e){
            throw new  NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
    }

    public List<ExhibitType> getTypes() {
        return Arrays.asList(ExhibitType.values());
    }

    @Override
    public List<Exhibit> findByAudience(Audience audience) {
        List<Exhibit> exhibitList = exhibitDao.loadForeignFields(exhibitDao.findByAudience(audience));
        if(exhibitList.size() < 1){
            throw new NotFoundException(ErrorMessage.OBJECT_NOT_FOUND);
        }
        return exhibitList;
    }
}
