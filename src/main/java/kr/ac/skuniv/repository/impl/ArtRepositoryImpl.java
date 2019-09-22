package kr.ac.skuniv.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.domain.dto.ReplyDto;
import kr.ac.skuniv.domain.entity.*;
import kr.ac.skuniv.repository.custom.ArtRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Component
public class ArtRepositoryImpl extends QuerydslRepositorySupport implements ArtRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    private QArt art = QArt.art;
    private QReply reply = QReply.reply;
    private QMember member = QMember.member;

    public ArtRepositoryImpl() {
        super(Art.class);
    }

    private final int DEFAULT_LIMIT_SIZE = 9;

    @Override
    public Page<ArtDto> searchArt(String searchKeyword, int pageNum) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(ArtDto.class, art.id, art.artName, art.price, art.rentCheck, member.name))
                .from(art)
                .join(art.member, member)
                .where(art.artName.contains(searchKeyword).or(art.member.name.contains(searchKeyword)))
                .orderBy(art.id.desc())
                ;
        List<ArtDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtDto> getArtsByUserId(int pageNum, String userId) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(ArtDto.class, art.id, art.artName, art.price, art.rentCheck, member.name))
                .from(art)
                .join(art.member, member)
                .where(art.member.id.eq(userId))
                .orderBy(art.id.desc())
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtDto> getAllArts(int pageNum) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(ArtDto.class, art.id, art.artName, art.price, art.rentCheck, member.name))
                .from(art)
                .join(art.member, member)
                .orderBy(art.id.desc())
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public ArtDto getArtDetail(Long artNo) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(ArtDto.class, art.id, art.artName, art.price, art.rentCheck, art.explanation, art.regDate, member.name))
                .from(art)
                .join(art.member, member)
                .where(art.id.eq(artNo));

        ArtDto artDto = jpaQuery.fetchOne();

        List<Reply> replies = jpaQuery
                .join(art.replies, reply)
                .transform(groupBy(art.id).as(list(reply))) //art.id 끼리 묶어서 reply 를 가져온다
                .get(artNo); //art.id 가 artNo 와 같은 걸 가져온다

        List<ReplyDto> replyDtos = new ArrayList<>();

        for(Reply reply : replies){
            replyDtos.add(
                    ReplyDto.builder()
                        .title(reply.getTitle())
                        .content(reply.getContent())
                        .regDate(reply.getRegDate())
                        .updateDate(reply.getUpdateDate())
                        .replyNo(reply.getReplyNo())
                        .userId(reply.getMember().getId())
                        .build()
            );
        }


        return new ArtDto().setReply(replyDtos);
    }
}
