package kr.ac.skuniv.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.domain.dto.ArtRequestDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.QArt;
import kr.ac.skuniv.repository.custom.ArtRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ArtRepositoryCustomImpl extends QuerydslRepositorySupport implements ArtRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    private QArt art = QArt.art;

    public ArtRepositoryCustomImpl() {
        super(Art.class);
    }

    @Override
    public List<ArtRequestDto> searchArt(String keyword) {
        JPAQuery<ArtRequestDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(art)
                .from(art)
                .where(art.artName.contains(keyword));
        List<ArtRequestDto> arts = jpaQuery.fetch();
        return arts;
    }
}
