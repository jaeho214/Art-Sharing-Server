package kr.ac.skuniv.artsharing.domain.dto.art;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtUpdateDto {
    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String explanation; //설명

    @Builder
    public ArtUpdateDto(Long id, String artName, String price, String explanation) {
        this.id = id;
        this.artName = artName;
        this.price = price;
        this.explanation = explanation;
    }
}
