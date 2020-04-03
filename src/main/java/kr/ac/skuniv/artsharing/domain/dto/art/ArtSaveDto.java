package kr.ac.skuniv.artsharing.domain.dto.art;

import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ArtSaveDto {
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String explanation; //설명

    public Art of(Member member) {
        return Art.builder()
                .artName(artName)
                .price(price)
                .explanation(explanation)
                .isRent(false)
                .member(member)
                .replies(new ArrayList<>())
                .build();
    }
}
