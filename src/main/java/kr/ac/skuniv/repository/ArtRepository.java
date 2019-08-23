package kr.ac.skuniv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.skuniv.entity.Art;

public interface ArtRepository extends JpaRepository<Art, Long> {
	
	List<Art> findByMemberId(Long memberId);

}
