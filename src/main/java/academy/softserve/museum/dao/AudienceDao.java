package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import java.util.Optional;

public interface AudienceDao extends Crud<Audience> {

    Optional<Audience> findByName(String name);

}
