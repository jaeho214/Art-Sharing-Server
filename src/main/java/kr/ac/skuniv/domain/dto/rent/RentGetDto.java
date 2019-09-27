package kr.ac.skuniv.domain.dto.rent;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RentGetDto {
    private Long rentNo;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private String price;
    private String member;
    private Long artNo;
    private boolean isRent;
}
