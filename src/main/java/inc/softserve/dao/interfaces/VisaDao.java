package inc.softserve.dao.interfaces;

import inc.softserve.entities.Visa;

import java.util.Optional;
import java.util.Set;

public interface VisaDao extends Read<Visa> {

    Visa save(Visa visa);

    Set<Visa> findAll();

    Optional<Visa> findById(Long visaId);

    Optional<Visa> findByVisaNumber(String visaNumber);

    Set<Visa> findVisasByCountryId(Long countryId);

    Set<Visa> findVisasByUserId(Long usrId);

    int issuedVisas(String country);

    int issuedVisas(Long countryId);

    int usrHasVisas(String email);

    int usrHasVisas(Long usrId);
}
