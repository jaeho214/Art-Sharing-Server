package kr.ac.skuniv.repository;

import kr.ac.skuniv.domain.entity.RentalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalHistoryRepository extends JpaRepository<RentalHistory, Long> {

}
