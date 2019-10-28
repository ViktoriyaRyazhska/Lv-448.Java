package inc.softserve.services.intefaces;

import inc.softserve.entities.Country;
import inc.softserve.entities.Visa;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface VisaStatsService {

    Optional<Integer> countVisasByUserEmail(String email);

    Optional<Integer> countVisasIssuedByCountry(String country);

    Map<String, Integer> countVisasIssuedByAllCountry();

    Set<Visa> issuedVisasToUsr(Long usrId);

    Set<Country> visitedCountries(Long usrId);
}
