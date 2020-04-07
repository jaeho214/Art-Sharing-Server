package kr.ac.skuniv.artsharing.domain.dto.rent;

import kr.ac.skuniv.artsharing.domain.entity.rent.Rent;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentGetDto {
    private Long rent_id;
    private LocalDateTime rentDate;
    private LocalDate returnDate;
    private String price;
    private String member;
    private Long art_id;
    private boolean isRent;

    @Builder
    public RentGetDto(Long rent_id, LocalDateTime rentDate, LocalDate returnDate, String price, String member, Long art_id, boolean isRent) {
        this.rent_id = rent_id;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.price = price;
        this.member = member;
        this.art_id = art_id;
        this.isRent = isRent;
    }

    public static RentGetDto of(Rent rent){
        return RentGetDto.builder()
                .rent_id(rent.getId())
                .rentDate(rent.getCreatedAt())
                .returnDate(rent.getReturnDate())
                .price(rent.getPrice())
                .member(rent.getMember().getUserId())
                .art_id(rent.getArt().getId())
                .isRent(rent.getArt().isRent())
                .build();

    }
}
