package kr.ac.skuniv.artsharing.domain.dto.art;

import kr.ac.skuniv.artsharing.domain.dto.ReplyDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ArtUpdateDto {
    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String explanation; //설명
    //private String userId;
    private boolean isRent = false; // TODO : 대여를 하게 되면 true로 바꿔주기
    private List<ReplyDto> replyList = new ArrayList<>();
    private String imageUrl;
}
