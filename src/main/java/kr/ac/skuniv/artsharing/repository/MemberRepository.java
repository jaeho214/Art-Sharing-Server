package kr.ac.skuniv.artsharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.skuniv.artsharing.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findById(String userId);
}