package inc.softserve.dao.interfaces;

import inc.softserve.entities.Usr;

import java.util.Optional;
import java.util.Set;

public interface UsrDao extends Read<Usr> {

    Usr save(Usr usr);

    Set<Usr> findAll();

    Optional<Usr> findById(Long usrId);

    Optional<Usr> findByEmail(String email);
}
