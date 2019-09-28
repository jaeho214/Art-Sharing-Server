package kr.ac.skuniv.artsharing.domain.dto.reply;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReplyGetDto {
    private Long replyNo;
    private String title;
    private String content;
    private String userId;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

}
