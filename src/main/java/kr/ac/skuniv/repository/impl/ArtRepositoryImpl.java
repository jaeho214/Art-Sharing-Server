package kr.ac.skuniv.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.QArt;
import kr.ac.skuniv.repository.custom.ArtRepositoryCustom;
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

    public ArtRepositoryImpl() {
        super(Art.class);
    }

    @Override
    public List<ArtDto> searchArt(String searchKeyword) {
        JPAQuery<ArtDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(art)
                .from(art)
                .where(art.artName.contains(searchKeyword));
        List<ArtDto> arts = jpaQuery.fetch();
        return arts;
    }
}
