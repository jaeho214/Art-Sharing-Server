package kr.ac.skuniv.artsharing.domain.dto.art;

import kr.ac.skuniv.artsharing.config.LocalDateConfig;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ArtSaveDto {
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String explanation; //설명
//    private boolean isRent = false; // TODO : 대여를 하게 되면 true로 바꿔주기



    public Art toEntity(Member member) {
        return Art.builder()
                .artName(artName)
                .price(price)
                .explanation(explanation)
                .member(member)
                .build();
    }
}