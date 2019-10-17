package entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private Long id;
    private LocalDate dateOfOrder;
    private LocalDate dateOfReturn;
    private User user;
    private BookInstance bookInstance;
}
