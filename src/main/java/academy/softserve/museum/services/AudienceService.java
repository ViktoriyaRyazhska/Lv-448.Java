package academy.softserve.museum.services;

import academy.softserve.museum.entities.Audience;
import java.util.List;
import java.util.Optional;

public interface AudienceService {

    boolean save(Audience objectToSave);

    boolean deleteById(long id);

    Optional<Audience> findById(long id);

    Optional<Audience> findByName(String name);

    List<Audience> findAll();

    boolean update(Audience newObject);

}
