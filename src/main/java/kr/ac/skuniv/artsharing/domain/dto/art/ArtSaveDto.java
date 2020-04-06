package kr.ac.skuniv.artsharing.domain.dto.art;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import lombok.*;

import java.util.ArrayList;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtSaveDto {
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String explanation; //설명

    @Builder
    public ArtSaveDto(String artName, String price, String explanation) {
        this.artName = artName;
        this.price = price;
        this.explanation = explanation;
    }

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
