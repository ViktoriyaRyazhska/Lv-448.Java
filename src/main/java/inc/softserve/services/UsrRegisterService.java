package inc.softserve.services;

import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;

public interface UsrRegisterService {

    String register(UsrDto usrDto, VisaDto visaDto);
}
