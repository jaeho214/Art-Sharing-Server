package kr.ac.skuniv.artsharing.domain.dto.art;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ArtGetDto {
    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String userId;
    private boolean isRent; // TODO : 대여를 하게 되면 true로 바꿔주기
    private String imageUrl;
}
