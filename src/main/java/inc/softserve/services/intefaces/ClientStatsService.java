package inc.softserve.services.intefaces;

import inc.softserve.entities.Usr;

import java.util.Optional;

public interface ClientStatsService {

    Optional<Usr> getUser(String email);
}
