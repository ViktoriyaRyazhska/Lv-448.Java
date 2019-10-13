package inc.softserve.dao.interfaces;

import inc.softserve.entities.Visa;

import java.util.List;
import java.util.Optional;

public interface VisaDao {

    List<Visa> findAll();

    Optional<Visa> findById(Long visaId);

    Optional<Visa> findByVisaNumber(String visaNumber);

    List<Visa> findVisasByCountryId(Long countryId);

    List<Visa> findVisasByUserId(Long usrId);

    int issuedVisas(String country);

    int issuedVisas(Long countryId);

    int usrHasVisas(String email);

    int usrHasVisas(Long usrId);
}
