package kr.ac.skuniv.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class RentDto {
    private Long artNo;
    private LocalDate rentDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
    private String price;
    private String member;

    @Builder
    public RentDto(Long artNo, LocalDate rentDate, LocalDate returnDate, String price, String member) {
        this.artNo = artNo;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.price = price;
        this.member = member;
    }
}
