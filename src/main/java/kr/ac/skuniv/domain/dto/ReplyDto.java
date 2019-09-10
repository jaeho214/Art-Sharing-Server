package kr.ac.skuniv.domain.dto;

import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class ReplyDto {
    private Long replyNo;
    private String title;
    private String content;
    private Art art;
    private Member member;
    private Timestamp regDate;
    private Timestamp updateDate;


    @Builder
    public ReplyDto(Long replyNo, String title, String content, Art art, Member member, Timestamp regDate, Timestamp updateDate) {
        this.replyNo = replyNo;
        this.title = title;
        this.content = content;
        this.art = art;
        this.member = member;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }


    public Reply toEntity(){
        return Reply.builder()
                .title(this.title)
                .content(this.content)
                .art(this.art)
                .member(this.member)
                .regDate(this.regDate)
                .updateDate(this.updateDate)
                .build();
    }
}
