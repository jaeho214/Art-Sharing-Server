package kr.ac.skuniv.artsharing.domain.dto.art;

import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.ArtImage;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtGetDto {
    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String userId;
    private boolean isRent; // TODO : 대여를 하게 되면 true로 바꿔주기
    private String imageUrl;

    @Builder
    public ArtGetDto(Long id, String artName, String price, String userId, boolean isRent, String imageUrl) {
        this.id = id;
        this.artName = artName;
        this.price = price;
        this.userId = userId;
        this.isRent = isRent;
        this.imageUrl = imageUrl;
    }

    public static ArtGetDto of(Art art, ArtImage artImage){
        return ArtGetDto.builder()
                .id(art.getId())
                .artName(art.getArtName())
                .price(art.getPrice())
                .userId(art.getMember().getUserId())
                .imageUrl(artImage.getImageUrl())
                .isRent(art.isRent())
                .build();
    }

    public static ArtGetDto of(Art art){
        return ArtGetDto.builder()
                .id(art.getId())
                .artName(art.getArtName())
                .price(art.getPrice())
                .userId(art.getMember().getUserId())
                .imageUrl(null)
                .isRent(art.isRent())
                .build();
    }
}
