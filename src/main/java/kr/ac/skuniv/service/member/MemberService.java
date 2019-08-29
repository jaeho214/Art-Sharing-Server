package kr.ac.skuniv.service.member;

import kr.ac.skuniv.domain.dto.SignUpDto;
import kr.ac.skuniv.domain.roles.MemberRole;
import org.springframework.stereotype.Service;

import kr.ac.skuniv.domain.dto.MemberRequest;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.exception.MemberException;
import kr.ac.skuniv.repository.MemberRepository;

@Service
public class MemberService {

	final private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	//회원가입
	// roles은 security 관련인데 고객이냐 작가냐에 따라 다르게 가입시키려고 한거야 시큐리티 적용하면서 할게
	public void signUpMember(MemberRequest request, MemberRole roles) {
			Member existMember = memberRepository.findByIdentity(request.getIdentity());

			//아아디 존재하면 에러 출력
			if(existMember != null)
				throw new MemberException("이미 존재하는 아이디입니다.");

			/*
			type 이 작가냐 고객이냐를 나누는거라는 전제하에
			작가냐 고객이냐를 체크박스로 해서 서버에서 받는게 아니라 url로 설정해주자 /client /artist 이렇게
			그래서 그 url에 따라 다르게 회원가입을 할 수 있게끔 어때?
			아니면 체크박스말고 그냥 작가 회원가입, 고객 회원가입을 따로 둘 수도 있고
			*/
			if(roles.equals(MemberRole.CLIENT))
				request.setType("CLIENT");
			else if(roles.equals(MemberRole.ARTIST))
				request.setType("ARTIST");
			else if(roles.equals(MemberRole.ADMIN))
				request.setType("ADMIN");

			Member member = request.toEntity();
			memberRepository.save(member);
	}

	//내정보 수정
	public void updateMember(MemberRequest request) {
		
		Member member = memberRepository.findByIdentity(request.getIdentity());
		
		member.updateMember(request);
		
		memberRepository.save(member);

	}

	//정보 삭제 -> 회원 탈퇴
	public void deleteMember(String userId) {
		
		Member member = memberRepository.findByIdentity(userId);
		
		memberRepository.delete(member);
		
	}

	//로그인
	public String loginMember(SignUpDto signUpDto) {
		
		Member login = memberRepository.findByIdentity(signUpDto.getId());

		//등록되어있지않으면 에러
		if(login.getIdentity() == null) {
			throw new MemberException("존재하지 않는 아이디입니다.");
		}

		if(!(login.getPassword().equals(signUpDto.getPw()))) {
			throw new MemberException("비밀번호가 틀렸습니다.");
		}

		return "시큐리티 적용해서 토큰 리턴할거야";
	}

	//회원 정보 열람
	public MemberRequest memberInfo(String id){
		Member member = memberRepository.findByIdentity(id);

		return MemberRequest.builder()
				.identity(member.getIdentity())
				.name(member.getName())
				.group(member.getGroup())
				.phone(member.getPhone())
				.sex(member.getSex())
				.type(member.getType())
				.age(member.getAge())
				.build();
	}
	
}
