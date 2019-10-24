package inc.softserve.services.intefaces;

import inc.softserve.dto.UsrDto;
import inc.softserve.dto.VisaDto;
import inc.softserve.entities.Usr;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public interface UsrRegisterService {

    Map<String, String> register(UsrDto usrDto, VisaDto visaDto);

    Usr login(String email, String password);

    Map<String, String> validateData(String email, String password);

    UsrDto initUsrDto(String firstName, String lastName, String email, String phone, String date, String password);

    LocalDate generateDate(String date);

    VisaDto initVisaDto(String country, String ExpirationDate, String issued, String visaNumber);
}
