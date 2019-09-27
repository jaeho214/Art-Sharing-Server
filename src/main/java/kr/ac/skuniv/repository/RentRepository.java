package kr.ac.skuniv.repository;

import kr.ac.skuniv.domain.entity.Rent;
import kr.ac.skuniv.repository.custom.RentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long>, RentRepositoryCustom {
    List<Rent> findByArt_Id(Long artNo);
    List<Rent> findByMember_Id(String userId);
}
