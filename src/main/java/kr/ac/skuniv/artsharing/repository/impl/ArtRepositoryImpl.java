package kr.ac.skuniv.artsharing.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplySaveDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.entity.*;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.repository.custom.ArtRepositoryCustom;
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
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Component
public class ArtRepositoryImpl extends QuerydslRepositorySupport implements ArtRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    private QArt art = QArt.art;
    private QReply reply = QReply.reply;
    private QArtImage artImage = QArtImage.artImage;

    public ArtRepositoryImpl() {
        super(Art.class);
    }

    private final int DEFAULT_LIMIT_SIZE = 9;

    @Override
    public Page<ArtGetDto> searchArt(String searchKeyword, int pageNum) {
        JPAQuery<ArtGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery.where(art.artName.contains(searchKeyword).or(art.member.name.contains(searchKeyword).and(art.member.role.eq(MemberRole.ARTIST))))
                .orderBy(art.id.desc())
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtGetDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtGetDto> getArtsByUserId(int pageNum, String userId) {
        JPAQuery<ArtGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery.where(art.member.id.eq(userId))
                .orderBy(art.id.desc())
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtGetDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtGetDto> getAllArts(int pageNum) {
        JPAQuery<ArtGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery.orderBy(art.id.desc())
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtGetDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Optional<ArtGetDetailDto> getArtDetail(Long artNo) {
        JPAQuery<ArtGetDetailDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(ArtGetDetailDto.class, art.id, art.artName, art.price, art.isRent, art.explanation, art.regDate, art.member.name, artImage.imageUrl))
                .from(artImage)
                .join(artImage.art, art)
                .where(art.id.eq(artNo));

        ArtGetDetailDto artDto = jpaQuery.fetchOne();

        List<Reply> replies = jpaQuery
                .join(artImage.art.replies, reply)
                .transform(groupBy(art.id).as(list(reply))) //art.id 끼리 묶어서 reply 를 가져온다
                .get(artNo); //art.id 가 artNo 와 같은 걸 가져온다

        List<ReplyGetDto> replyGetDtos = new ArrayList<>();

        if(replies != null) {
            for (Reply reply : replies) {
                replyGetDtos.add(
                        ReplyGetDto.builder()
                                .title(reply.getTitle())
                                .content(reply.getContent())
                                .regDate(reply.getRegDate())
                                .updateDate(reply.getUpdateDate())
                                .replyNo(reply.getReplyNo())
                                .userId(reply.getMember().getId())
                                .build()
                );
            }
        }
        return Optional.ofNullable(artDto.setReply(replyGetDtos));
    }


    private JPAQuery<ArtGetDto> setQuery(JPAQuery<ArtGetDto> query){
        return query.select(Projections.constructor(ArtGetDto.class, art.id, art.artName, art.price, art.member.name, art.isRent, artImage.imageUrl))
                .from(artImage)
                .join(artImage.art, art);
    }
}
