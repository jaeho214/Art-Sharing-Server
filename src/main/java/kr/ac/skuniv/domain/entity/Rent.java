package kr.ac.skuniv.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

//TODO : 대여DB 생각해보자, DB설계를 다시 제대로 해보자

@Entity
@Getter
@NoArgsConstructor
public class Rent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentNo;

    @CreationTimestamp
    private LocalDate rentDate; // 대여날짜는 대여를 하고 대여 기록이 DB에 삽입될 때 생성

    private LocalDate returnDate; // 반납날짜는 프론트에서 request로 날짜를 받아서
    private String price; // 대여 비용

    @ManyToOne
    @JoinColumn(name = "renter")
    private Member member; //대여인

    @ManyToOne
    @JoinColumn(name = "art_No")
    private Art art;

    @Builder
    public Rent(Long rentNo, LocalDate rentDate, LocalDate returnDate, String price, Member member, Art art) {
        this.rentNo = rentNo;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.price = price;
        this.member = member;
        this.art = art;
    }
}
