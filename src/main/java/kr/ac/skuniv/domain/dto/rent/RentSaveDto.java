package kr.ac.skuniv.domain.dto.rent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kr.ac.skuniv.config.LocalDateConfig;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.entity.Rent;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RentSaveDto {
//    private Long artNo;

    @JsonDeserialize(using = LocalDateConfig.class)
    private LocalDate returnDate;
    private String price;

    public Rent toEntity(Member member, Art art) {
        return Rent.builder()
                .price(price)
                .returnDate(returnDate)
                .member(member)
                .art(art)
                .build();

    }
}
