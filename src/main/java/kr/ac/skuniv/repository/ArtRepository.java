package kr.ac.skuniv.repository;

import kr.ac.skuniv.domain.entity.Art;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtRepository extends JpaRepository<Art, Long> {

	List<Art> findByMemberId(String memberId);

}
