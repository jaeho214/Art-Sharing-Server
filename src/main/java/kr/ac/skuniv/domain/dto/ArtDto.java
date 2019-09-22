package kr.ac.skuniv.domain.dto;

import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArtDto {

    private Long id; //작품 등록 번호
    private String artName; //작품 이름
    private String price; //하루당 가격
    private String explanation; //설명
    private LocalDate regDate;
    private String userId;
    private boolean rentCheck;
    private List<ReplyDto> replyList = new ArrayList<>();

    @Builder
    public ArtDto(Long id, String artName, String price, String explanation,LocalDate regDate, String userId, boolean rentCheck, List<ReplyDto> replyList) {
        this.id = id;
        this.artName = artName;
        this.price = price;
        this.explanation = explanation;
        this.regDate = regDate;
        this.userId = userId;
        this.rentCheck = rentCheck;
        this.replyList = replyList;
    }

    public ArtDto setReply(List<ReplyDto> replyList) {
        this.replyList = replyList;
        return this;
    }

    public Art toEntity(Member member) {
        return Art.builder()
                .artName(artName)
                .price(price)
                .explanation(explanation)

                .member(member)
                .build();
    }

}
