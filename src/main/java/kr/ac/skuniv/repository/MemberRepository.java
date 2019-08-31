package kr.ac.skuniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.skuniv.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findById(String userId);
}