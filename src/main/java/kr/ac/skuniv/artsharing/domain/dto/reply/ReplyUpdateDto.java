package kr.ac.skuniv.artsharing.domain.dto.reply;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyUpdateDto {
    private Long replyNo;
    private String title;
    private String content;

    public ReplyUpdateDto(Long replyNo, String title, String content) {
        this.replyNo = replyNo;
        this.title = title;
        this.content = content;
    }
}
