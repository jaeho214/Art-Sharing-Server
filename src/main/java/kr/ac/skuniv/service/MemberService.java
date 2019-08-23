package kr.ac.skuniv.service;

import org.springframework.stereotype.Service;

import kr.ac.skuniv.dto.MemberRequest;
import kr.ac.skuniv.entity.Member;
import kr.ac.skuniv.exception.MemberException;
import kr.ac.skuniv.repository.MemberRepository;

@Service
public class MemberService {

	final private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	//회원가입
	public void SignupMember(MemberRequest request) {
		
		try {
			Member existMember = memberRepository.findByIdentity(request.getIdentity());

			//아이디 존재하지않으면 새로 만들기
			if(existMember == null) {
				
				Member member = request.toEntity();
				
				memberRepository.save(member);
				
			}
			//아아디 존재하면 에러 출력
			else {
				throw new MemberException("Error");
			}
		} catch(Exception e) {
			throw new MemberException("Error");
		}
		
	}

	//내정보 수정
	public void updateMember(MemberRequest request) {
		
		Member member = memberRepository.findByIdentity(request.getIdentity());
		
		member.updateMember(request);
		
		memberRepository.save(member);
		
		
	}

	//정보 삭제 -> 회원 탈퇴
	public void deleteMember(MemberRequest request) {
		
		Member member = memberRepository.findByIdentity(request.getIdentity());
		
		memberRepository.delete(member);
		
	}

	//로그인
	public void loginMember(MemberRequest request) {
		
		Member login = memberRepository.findByIdentity(request.getIdentity());

		//등록되어있지않으면 에러
		if(login.getIdentity() == null) {
			throw new MemberException("ID Error");
		}
		//등록 되어있음
		else {
			//패스워드 일치하면 로그인 성공
			if(login.getPassword().equals(request.getPassword())) {
				System.out.println("Login Success");
			}
			//아니면 실패
			else {
				throw new MemberException("Password Error");
			}
		}
		
	}
	
}
