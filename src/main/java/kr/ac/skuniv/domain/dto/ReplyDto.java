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

@Getter @Setter
@NoArgsConstructor
public class ReplyDto {
    private Long replyNo;
    private String title;
    private String content;
    private Long artNo;
    private String userId;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;


    @Builder
    public ReplyDto(Long replyNo, String title, String content, Long artNo, String userId, LocalDateTime regDate, LocalDateTime updateDate) {
        this.replyNo = replyNo;
        this.title = title;
        this.content = content;
        this.artNo = artNo;
        this.userId = userId;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }


    public Reply toEntity(Member member, Art art){
        return Reply.builder()
                .title(this.title)
                .content(this.content)
                .art(art)
                .member(member)
                .regDate(this.regDate)
                .updateDate(this.updateDate)
                .build();
    }
}
