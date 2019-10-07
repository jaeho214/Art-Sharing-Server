package kr.ac.skuniv.artsharing.domain.dto.art;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kr.ac.skuniv.artsharing.config.LocalDateConfig;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplySaveDto;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtGetDetailDto {

    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private boolean isRent; // TODO : 대여를 하게 되면 true로 바꿔주기
    private String explanation; //설명

    @JsonDeserialize(using = LocalDateConfig.class)
    private LocalDate regDate;
    private String artistName;
    private String imageUrl;

    private List<ReplyGetDto> replyList = new ArrayList<>();

    @Builder
    public ArtGetDetailDto(Long id, String artName, String price, boolean isRent, String explanation, LocalDate regDate, String artistName, String imageUrl) {
        this.id = id;
        this.artName = artName;
        this.price = price;
        this.isRent = isRent;
        this.explanation = explanation;
        this.regDate = regDate;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }

    public ArtGetDetailDto setReply(List<ReplyGetDto> replyList) {
        this.replyList = replyList;
        return this;
    }
}
