package kr.ac.skuniv.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class RentalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    //대여 상태 확인(완료 / 진행중)
    private boolean historyCheck;

    //대여 중인 작품
    @OneToOne
    @JoinColumn(name = "rental_art")
    private Art art;

    //대여 중인 작가 / 소비자 가져오기
    @ManyToOne
    @JoinColumn(name = "rental_member")
    private Member member;

    @Builder
    public RentalHistory(boolean historyCheck) {
        this.historyCheck = historyCheck;
    }

}
