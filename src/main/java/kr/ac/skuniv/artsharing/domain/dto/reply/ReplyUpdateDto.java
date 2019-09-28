package kr.ac.skuniv.artsharing.domain.dto.reply;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReplyUpdateDto {
    private Long replyNo;
    private String title;
    private String content;
    //private Long artNo;
}
