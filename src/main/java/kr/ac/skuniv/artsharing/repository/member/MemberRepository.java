package kr.ac.skuniv.artsharing.repository.member;

import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findByUserId(String userId);
	List<Member> findByRole(MemberRole role);
	Boolean existsByUserId(String userId);
}