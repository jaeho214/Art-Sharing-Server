package kr.ac.skuniv.artsharing.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.ac.skuniv.artsharing.domain.dto.ReplyDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;

    private String title;
    private String content;

    //양방향 매핑
    @JsonIgnore // JSON 형태로 변환될 때 제외
    @ManyToOne(fetch = FetchType.LAZY)
    private Art art;

    @ManyToOne
    @JoinColumn(name = "replyer")
    private Member member;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder
    public Reply(String title, String content, Art art, Member member, LocalDateTime regDate, LocalDateTime updateDate) {
        this.title = title;
        this.content = content;
        this.art = art;
        this.member = member;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }

    public void updateReply(ReplyDto replyDto){
        this.title = replyDto.getTitle();
        this.content = replyDto.getContent();
    }
}
