package kr.ac.skuniv.artsharing.domain.entity;

import kr.ac.skuniv.artsharing.util.JpaBasePersistable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "rent_id"))
@Where(clause = "deleted=0")
@DynamicUpdate
public class Rent extends JpaBasePersistable {
    @CreationTimestamp
    private LocalDate rentDate; // 대여날짜는 대여를 하고 대여 기록이 DB에 삽입될 때 생성

    private LocalDate returnDate; // 반납날짜는 프론트에서 request로 날짜를 받아서
    private String price; // 대여 비용

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "renter")
    private Member member; //대여인

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "art_No")
    private Art art;

    @Builder
    public Rent(LocalDate rentDate, LocalDate returnDate, String price, Member member, Art art) {
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.price = price;
        this.member = member;
        this.art = art;
    }
}
