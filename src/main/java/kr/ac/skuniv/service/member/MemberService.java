package kr.ac.skuniv.service.member;

import kr.ac.skuniv.domain.dto.MemberRequest;
import kr.ac.skuniv.domain.dto.SignUpDto;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.exception.MemberException;
import kr.ac.skuniv.repository.MemberRepository;
import kr.ac.skuniv.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	final private MemberRepository memberRepository;
	final private JwtProvider jwtProvider;
	final private PasswordEncoder passwordEncoder;
	
	public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.jwtProvider = jwtProvider;
		this.passwordEncoder = passwordEncoder;
	}

	//회원가입
	public void signUpMember(MemberRequest request, MemberRole roles) {
			Member existMember = memberRepository.findById(request.getId());

			//아아디 존재하면 에러 출력
			if(existMember != null)
				throw new MemberException("이미 존재하는 아이디입니다.");

			Member member = request.toEntity();
			member.setPassword(passwordEncoder.encode(member.getPassword()));

			if(roles.equals(MemberRole.CLIENT)){
				member.setRole("CLIENT");
			}
			else if(roles.equals(MemberRole.ARTIST)){
				member.setRole("ARTIST");
			}

			memberRepository.save(member);
	}

	//내정보 수정
	public void updateMember(MemberRequest request) {
		
		Member member = memberRepository.findById(request.getId());

		if(member == null)
			throw new MemberException("존재하지 않는 회원입니다");

		request.setPassword(passwordEncoder.encode(request.getPassword()));

		member.updateMember(request);
		
		memberRepository.save(member);

	}

	//정보 삭제 -> 회원 탈퇴
	public void deleteMember(String userId) {
		
		Member member = memberRepository.findById(userId);
		
		memberRepository.delete(member);
		
	}

	//로그인
	public String loginMember(SignUpDto signUpDto) {
		
		Member login = memberRepository.findById(signUpDto.getId());

		//등록되어있지않으면 에러
		if(login.getId() == null) {
			throw new MemberException("존재하지 않는 아이디입니다.");
		}

		if(!passwordEncoder.matches(signUpDto.getPw(), login.getPassword())) {
			throw new MemberException("비밀번호가 틀렸습니다.");
		}

		return jwtProvider.createToken(login.getId(), login.getRole());
	}

	//회원 정보 열람
	public MemberRequest memberInfo(String token){
		String userId = jwtProvider.getUserIdByToken(token);
		Member member = memberRepository.findById(userId);

		if(member == null)
			throw new MemberException("존재하지 않는 회원입니다");

		return MemberRequest.builder()
				.id(member.getId())
				.name(member.getName())
				.affiliation(member.getAffiliation())
				.phone(member.getPhone())
				.sex(member.getSex())
				.age(member.getAge())
				.build();
	}
	
}
