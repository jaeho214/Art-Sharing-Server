package kr.ac.skuniv.domain.dto.art;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kr.ac.skuniv.config.LocalDateConfig;
import kr.ac.skuniv.domain.dto.ReplyDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ArtGetDetailDto {

    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String explanation; //설명

    @JsonDeserialize(using = LocalDateConfig.class)
    private LocalDate regDate;
    private String userId;
    private boolean isRent = false; // TODO : 대여를 하게 되면 true로 바꿔주기
    private List<ReplyDto> replyList = new ArrayList<>();
    private String imageUrl;

    public ArtGetDetailDto setReply(List<ReplyDto> replyList) {
        this.replyList = replyList;
        return this;
    }
}
