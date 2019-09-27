package kr.ac.skuniv.artsharing.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.entity.QArt;
import kr.ac.skuniv.artsharing.domain.entity.QRent;
import kr.ac.skuniv.artsharing.domain.entity.Rent;
import kr.ac.skuniv.artsharing.repository.custom.RentRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RentRepositoryImpl extends QuerydslRepositorySupport implements RentRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    private QRent rent = QRent.rent;
    private QArt art = QArt.art;

    public RentRepositoryImpl(){ super(Rent.class); }

    private final int DEFAULT_LIMIT_SIZE = 10;


    @Override
    public RentGetDto findRecentRent(Long artNo) {
        JPAQuery<RentGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(rent)
                .from(rent)
                .where(rent.art.id.eq(artNo))
                .orderBy(rent.rentNo.desc())
                .limit(1);

        RentGetDto rent = jpaQuery.fetchOne();

        return rent;
    }

    @Override
    public Page<RentGetDto> findRentByArt(String userId, Long artNo, int pageNum) {
        JPAQuery<RentGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(RentGetDto.class, rent.rentNo, rent.rentDate, rent.returnDate, rent.price, rent.member.id, art.artName, art.isRent))
                .from(rent)
                .join(rent.art, art)
                .where(art.member.id.eq(userId).and(art.id.eq(artNo)))
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);

        List<RentGetDto> rentList = jpaQuery.fetch();

        return new PageImpl<>(rentList, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "rentNo")),rentList.size());
    }

    @Override
    public Page<RentGetDto> findRentByMember(String userId, int pageNum) {
        JPAQuery<RentGetDto> jpaQuery = new JPAQuery<>(entityManager);
        jpaQuery.select(Projections.constructor(RentGetDto.class, rent.rentNo, rent.rentDate, rent.returnDate, rent.price, rent.member.id, art.artName, art.isRent))
                .from(rent)
                .join(rent.art, art)
                .where(rent.member.id.eq(userId))
                .offset(--pageNum * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE);

        List<RentGetDto> rentList = jpaQuery.fetch();

        return new PageImpl<>(rentList, PageRequest.of(pageNum, DEFAULT_LIMIT_SIZE, new Sort(Sort.Direction.DESC, "rentNo")),rentList.size());
    }
}
