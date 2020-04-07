package kr.ac.skuniv.artsharing.repository.rent;

import kr.ac.skuniv.artsharing.domain.entity.rent.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long>, RentRepositoryCustom {
    List<Rent> findByArt_Id(Long artNo);
    List<Rent> findByMember_Id(String userId);
}
