package kr.ac.skuniv.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.QArt;
import kr.ac.skuniv.domain.entity.QReply;
import kr.ac.skuniv.repository.custom.ArtRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ArtRepositoryImpl extends QuerydslRepositorySupport implements ArtRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    private QArt art = QArt.art;
    private QReply reply = QReply.reply;

    public ArtRepositoryImpl() {
        super(Art.class);
    }

    private final int DEFAULT_LIMIT_SIZE = 9;

    @Override
    public Page<ArtDto> searchArt(String searchKeyword, int pageNum) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(ArtDto.class, art.id, art.artName, art.member, art.price, art.rentCheck, art.member.name))
                .from(art)
                .where(art.artName.contains(searchKeyword).or(art.member.name.contains(searchKeyword)))
                .orderBy(art.id.desc())
                ;
        List<ArtDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public Page<ArtDto> getArtsByUserId(int pageNum, String userId) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(ArtDto.class, art.id, art.artName, art.member, art.price, art.rentCheck, art.member.name))
                .from(art)
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
        jpaQuery.select(Projections.constructor(ArtDto.class, art.id, art.artName, art.member, art.price, art.rentCheck, art.member.name))
                .from(art)
                .orderBy(art.id.desc())
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);
        List<ArtDto> arts = jpaQuery.fetch();
        return new PageImpl<>(arts, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "id")),arts.size());
    }

    @Override
    public ArtDto getArtDetail(Long artNo) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);

        return new ArtDto();
    }
}
