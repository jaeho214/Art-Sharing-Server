package kr.ac.skuniv.artsharing.domain.dto.art;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kr.ac.skuniv.artsharing.config.LocalDateConfig;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtGetDetailDto {

    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private Boolean isRent;
    private String explanation; //설명

    @JsonDeserialize(using = LocalDateConfig.class)
    private LocalDateTime createdAt;
    private String artistName;
    private String imageUrl;

    private List<ReplyGetDto> replies = new ArrayList<>();

    @Builder
    public ArtGetDetailDto(Long id,
                           String artName,
                           String price,
                           Boolean isRent,
                           String explanation,
                           LocalDateTime createdAt,
                           String artistName,
                           String imageUrl,
                           List<ReplyGetDto> replies) {
        this.id = id;
        this.artName = artName;
        this.price = price;
        this.isRent = isRent;
        this.explanation = explanation;
        this.createdAt = createdAt;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
        this.replies = replies;
    }

    public void setReply(List<Reply> replyList) {
        if(replyList == null)
            return;
        replyList.forEach(
                reply -> this.replies.add(ReplyGetDto.of(reply))
        );
    }

    public static ArtGetDetailDto of(Art art){
        return ArtGetDetailDto.builder()
                .id(art.getId())
                .artName(art.getArtName())
                .price(art.getPrice())
                .isRent(art.isRent())
                .explanation(art.getExplanation())
                .createdAt(art.getCreatedAt())
                .artistName(art.getMember().getName())
                .imageUrl(art.getArtImage().getImageUrl())
                .replies(new ArrayList<>())
                .build();
    }
}
