package kr.ac.skuniv.domain.dto;

import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.entity.Rent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class RentDto {
    private Long rentNo;
    private Long artNo;
    private LocalDateTime rentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime returnDate;
    private String price;
    private String member;

    @Builder
    public RentDto(Long rentNo, Long artNo, LocalDateTime rentDate, LocalDateTime returnDate, String price, String member) {
        this.rentNo = rentNo;
        this.artNo = artNo;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.price = price;
        this.member = member;
    }

    public Rent toEntity(Member member, Art art) {
        return Rent.builder()
                .rentNo(rentNo)
                .price(price)
                .rentDate(rentDate)
                .returnDate(returnDate)
                .member(member)
                .art(art)
                .build();

    }
}
