package kr.ac.skuniv.repository;

import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.repository.custom.ArtRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtRepository extends JpaRepository<Art, Long>, ArtRepositoryCustom {

	List<Art> findAllByMemberId(String memberId);
	Art findByMemberIdAndId(String memberId, Long id);

}
