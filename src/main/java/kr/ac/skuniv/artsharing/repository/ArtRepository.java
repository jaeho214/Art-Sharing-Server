package kr.ac.skuniv.artsharing.repository;

import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.repository.custom.ArtRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtRepository extends JpaRepository<Art, Long>, ArtRepositoryCustom {

	List<Art> findAllByMemberId(String userId);
	Art findByMemberIdAndId(String userId, Long artNo);

}
