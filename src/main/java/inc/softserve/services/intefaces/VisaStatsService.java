package inc.softserve.services.intefaces;

import java.util.Optional;

public interface VisaStatsService {

    Optional<Integer> countVisasByUserEmail(String email);

    Optional<Integer> countVisasIssuedByCountry(String country);
}
