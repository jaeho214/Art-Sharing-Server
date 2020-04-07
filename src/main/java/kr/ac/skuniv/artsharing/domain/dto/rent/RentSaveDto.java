package kr.ac.skuniv.artsharing.domain.dto.rent;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.rent.Rent;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentSaveDto {
    //@JsonDeserialize(using = LocalDateConfig.class)
    private LocalDate returnDate;
    private String price;

    @Builder
    public RentSaveDto(LocalDate returnDate, String price) {
        this.returnDate = returnDate;
        this.price = price;
    }

    public Rent of(Member member, Art art) {
        return Rent.builder()
                .price(this.price)
                .returnDate(this.returnDate)
                .member(member)
                .art(art)
                .build();

    }
}
