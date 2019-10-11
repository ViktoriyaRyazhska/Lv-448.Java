package inc.softserve.dao;

import inc.softserve.entities.Usr;

import java.util.List;
import java.util.Optional;

public class UserCrudJdbs implements Crud<Usr> {
    @Override
    public int save(Usr usr) {
        return 0;
    }

    @Override
    public int delete(Usr usr) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<Usr> findAll() {
        return null;
    }

    @Override
    public Optional<Usr> findById(Long id) {
        return Optional.empty();
    }
}
