package inc.softserve.services.intefaces;

import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;

import java.sql.SQLException;

public interface UsrRegisterService {

    String register(UsrDto usrDto, VisaDto visaDto);
}
