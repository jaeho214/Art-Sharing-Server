package kr.ac.skuniv.artsharing.repository;

import kr.ac.skuniv.artsharing.domain.entity.Rent;
import kr.ac.skuniv.artsharing.repository.custom.RentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long>, RentRepositoryCustom {
    List<Rent> findByArt_Id(Long artNo);
    List<Rent> findByMember_Id(String userId);
}
