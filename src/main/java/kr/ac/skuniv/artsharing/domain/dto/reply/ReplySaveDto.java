package kr.ac.skuniv.artsharing.domain.dto.reply;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplySaveDto {
    private String title;
    private String content;
    private Long artNo;


    @Builder
    public ReplySaveDto(String title, String content, Long artNo) {
        this.title = title;
        this.content = content;
        this.artNo = artNo;
    }


    public Reply of(Member member, Art art){
        return Reply.builder()
                .title(this.title)
                .content(this.content)
                .art(art)
                .member(member)
                .build();
    }
}
