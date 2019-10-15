package inc.softserve.services.intefaces;

import inc.softserve.entities.Usr;

public interface FindUsr {

    Usr exists(String email);
}
