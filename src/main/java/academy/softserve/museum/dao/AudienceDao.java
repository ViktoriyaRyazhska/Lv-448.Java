package academy.softserve.museum.dao;

import academy.softserve.museum.entities.Audience;
import academy.softserve.museum.entities.Employee;
import academy.softserve.museum.entities.Exhibit;

import java.util.Optional;

public interface AudienceDao extends Crud<Audience> {

    Optional<Audience> findByName(String name);

    Optional<Audience> findByEmployee(Employee employee);

    Optional<Audience> findByExhibit(Exhibit exhibit);
}
