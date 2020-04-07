package kr.ac.skuniv.artsharing.domain.entity.rent;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.util.JpaBasePersistable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private LocalDate returnDate; // 반납날짜는 프론트에서 request로 날짜를 받아서
    private String price; // 대여 비용

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "renter")
    private Member member; //대여인

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "art_id")
    private Art art;

    @Builder
    public Rent(LocalDate returnDate, String price, Member member, Art art) {
        this.returnDate = returnDate;
        this.price = price;
        this.member = member;
        this.art = art;
    }

    public void updateReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;
        this.getArt().changeRentStatus(false);
    }
}
