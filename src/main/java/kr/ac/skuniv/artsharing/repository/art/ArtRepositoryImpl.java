package kr.ac.skuniv.artsharing.repository.art;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.art.QArt;
import kr.ac.skuniv.artsharing.domain.entity.artImage.QArtImage;
import kr.ac.skuniv.artsharing.domain.entity.member.QMember;
import kr.ac.skuniv.artsharing.domain.entity.reply.QReply;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ArtRepositoryImpl extends QuerydslRepositorySupport implements ArtRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    private QArt art = QArt.art;
    private QReply reply = QReply.reply;
    private QArtImage artImage = QArtImage.artImage;
    private QMember member = QMember.member;

    public ArtRepositoryImpl() {
        super(Art.class);
    }

    private final int DEFAULT_LIMIT_SIZE = 9;

    @Override
    public Page<ArtGetDto> searchArtByKeyword(String searchKeyword, int pageNo) {
        JPAQuery<ArtGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery.where(art.artName.contains(searchKeyword).or(art.member.name.contains(searchKeyword).and(art.member.role.eq(MemberRole.ARTIST))))
                .orderBy(art.id.desc())
                .offset(--pageNo * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtGetDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNo, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtGetDto> getArtsByUserId(String userId, int pageNo) {
        JPAQuery<ArtGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery.where(art.member.userId.eq(userId))
                .orderBy(art.id.desc())
                .offset(--pageNo * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtGetDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNo, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtGetDto> getAllArts(int pageNo) {
        JPAQuery<ArtGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery.orderBy(art.id.desc())
                .offset(--pageNo * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtGetDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNo, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtGetDto> getByRent(int pageNo) {
        JPAQuery<Art> jpaQuery = new JPAQuery<>(entityManager);

        List<Art> artList = jpaQuery.select(art)
                .from(art)
                .leftJoin(art.member, member).fetchJoin()
                .leftJoin(art.artImage, artImage).fetchJoin()
                .where(art.isRent.eq(false))
                .fetch();

        List<ArtGetDto> artGetDtoList = new ArrayList<>();
        artList.forEach(
                art -> artGetDtoList.add(
                        ArtGetDto.of(art)
                ));


        return new PageImpl<>(artGetDtoList,
                        PageRequest.of(--pageNo * 10, 10, Sort.Direction.DESC, "createdAt"),
                        jpaQuery.fetchCount());
    }

    @Override
    public Optional<ArtGetDetailDto> getArtDetail(Long art_id) {
        JPAQuery<Art> jpaQuery = new JPAQuery<>(entityManager);
        Art selectedArt = jpaQuery.select(art)
                .from(art)
                .leftJoin(art.artImage, artImage).fetchJoin()
                .leftJoin(art.replies, reply).fetchJoin()
                .leftJoin(reply.member, member).fetchJoin()
                .where(art.id.eq(art_id))
                .fetchOne();

        ArtGetDetailDto artDetail = ArtGetDetailDto.of(selectedArt);
        artDetail.setReply(selectedArt.getReplies());
        return Optional.ofNullable(artDetail);
    }


    private JPAQuery<ArtGetDto> setQuery(JPAQuery<ArtGetDto> query){
        return query.select(Projections.constructor(ArtGetDto.class, art.id, art.artName, art.price, art.member.name, art.isRent, artImage.imageUrl))
                .from(art)
                .leftJoin(art.artImage, artImage);
    }
}
