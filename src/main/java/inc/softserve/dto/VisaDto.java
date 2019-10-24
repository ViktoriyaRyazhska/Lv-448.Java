package inc.softserve.dto;

import inc.softserve.entities.Country;
import inc.softserve.entities.Usr;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VisaDto {
    private String visaNumber;
    private LocalDate issued;
    private LocalDate expirationDate;
    private String country;

    public VisaDto() {
    }

    public VisaDto(String visaNumber, LocalDate issued, LocalDate expirationDate, String country) {
        this.visaNumber = visaNumber;
        this.issued = issued;
        this.expirationDate = expirationDate;
        this.country = country;
    }
}
