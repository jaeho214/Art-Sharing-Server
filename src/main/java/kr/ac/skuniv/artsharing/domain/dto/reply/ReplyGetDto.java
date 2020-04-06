package kr.ac.skuniv.artsharing.domain.dto.reply;

import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyGetDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    @Builder
    public ReplyGetDto(Long id, String title, String content, String writer, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public static ReplyGetDto of(Reply reply){
        return ReplyGetDto.builder()
                .id(reply.getId())
                .title(reply.getTitle())
                .content(reply.getContent())
                .writer(reply.getMember().getUserId())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
