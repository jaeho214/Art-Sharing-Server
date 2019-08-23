package kr.ac.skuniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.skuniv.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findByIdentity(String identity);
	
}
