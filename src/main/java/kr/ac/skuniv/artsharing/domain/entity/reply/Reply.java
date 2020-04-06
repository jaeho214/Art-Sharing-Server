package kr.ac.skuniv.artsharing.domain.entity.reply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.util.JpaBasePersistable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "reply_id"))
@Where(clause = "deleted=0")
@DynamicUpdate
public class Reply extends JpaBasePersistable {
    private String title;
    private String content;

    //양방향 매핑
    @JsonIgnore // JSON 형태로 변환될 때 제외
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Art art;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "replyer")
    private Member member;


    @Builder
    public Reply(String title, String content, Art art, Member member) {
        this.title = title;
        this.content = content;
        this.art = art;
        this.member = member;
    }

    public void updateReply(ReplyUpdateDto replyUpdateDto){
        this.title = replyUpdateDto.getTitle();
        this.content = replyUpdateDto.getContent();
    }
}
