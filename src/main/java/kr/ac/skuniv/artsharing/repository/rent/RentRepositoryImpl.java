package kr.ac.skuniv.artsharing.repository.rent;

import com.querydsl.jpa.impl.JPAQuery;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;

import kr.ac.skuniv.artsharing.domain.entity.art.QArt;
import kr.ac.skuniv.artsharing.domain.entity.member.QMember;
import kr.ac.skuniv.artsharing.domain.entity.rent.QRent;
import kr.ac.skuniv.artsharing.domain.entity.rent.Rent;
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

@Component
public class RentRepositoryImpl extends QuerydslRepositorySupport implements RentRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    private QRent rent = QRent.rent;
    private QArt art = QArt.art;
    private QMember member = QMember.member;

    public RentRepositoryImpl(){ super(Rent.class); }

    private final int DEFAULT_LIMIT_SIZE = 10;


    @Override
    public RentGetDto findRentByArt_IdAndUserId(Long art_id, String userId) {
        JPAQuery<Rent> jpaQuery = new JPAQuery<>(entityManager);
        Rent rent = jpaQuery.select(this.rent)
                            .from(this.rent)
                            .where(this.rent.art.id.eq(art_id))
                            .where(this.rent.member.userId.eq(userId))
                            .orderBy(this.rent.id.desc())
                            .fetch().get(0);


        return RentGetDto.of(rent);
    }

    @Override
    public Page<RentGetDto> findRentByArt(Long art_id, int pageNo) {
        List<Rent> rentList = setQuery()
                .where(art.id.eq(art_id))
                .offset(--pageNo * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE)
                .fetch();


        return new PageImpl<>(toDtoList(rentList),
                        PageRequest.of(pageNo, DEFAULT_LIMIT_SIZE,
                        new Sort(Sort.Direction.DESC, "createdAt")),
                        rentList.size());
    }

    @Override
    public Page<RentGetDto> findRentByMember(String userId, int pageNo) {
        List<Rent> rentList = setQuery()
                .where(rent.member.userId.eq(userId))
                .offset(--pageNo * DEFAULT_LIMIT_SIZE)
                .limit(DEFAULT_LIMIT_SIZE)
                .fetch();


        return new PageImpl<>(toDtoList(rentList),
                        PageRequest.of(pageNo, DEFAULT_LIMIT_SIZE,
                        new Sort(Sort.Direction.DESC, "createdAt")),
                        rentList.size());
    }

    private List<RentGetDto> toDtoList(List<Rent> rentList){
        List<RentGetDto> rentGetDtoList = new ArrayList<>();

        rentList.forEach(
                rent -> rentGetDtoList.add(
                        RentGetDto.of(rent)
                )
        );
        return rentGetDtoList;
    }

    private JPAQuery<Rent> setQuery(){
        JPAQuery<Rent> jpaQuery = new JPAQuery<>(entityManager);
        return jpaQuery.select(rent)
                .from(rent)
                .leftJoin(rent.art, art).fetchJoin()
                .leftJoin(rent.member, member).fetchJoin();
    }
}
