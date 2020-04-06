package kr.ac.skuniv.artsharing.repository.art;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.entity.*;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.repository.art.ArtRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Page<ArtGetDto> searchArtByKeyword(String searchKeyword, int pageNum) {
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
    public Page<ArtGetDto> getArtsByUserId(String userId, int pageNum) {
        JPAQuery<ArtGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery = setQuery(jpaQuery);
        jpaQuery.where(art.member.userId.eq(userId))
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
