package kr.ac.skuniv.artsharing.repository;

import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.skuniv.artsharing.domain.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findById(String userId);
	List<Member> findByRole(MemberRole role);
}