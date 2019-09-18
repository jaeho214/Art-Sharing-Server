package kr.ac.skuniv.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Reply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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
    private Timestamp regDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @Builder
    public Reply(String title, String content, Art art, Member member, Timestamp regDate, Timestamp updateDate) {
        this.title = title;
        this.content = content;
        this.art = art;
        this.member = member;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }
}
