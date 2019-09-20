package kr.ac.skuniv.service.member;

import kr.ac.skuniv.domain.dto.MemberDto;
import kr.ac.skuniv.domain.dto.SignUpDto;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.repository.MemberRepository;
import kr.ac.skuniv.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class MemberService {

	final private MemberRepository memberRepository;
	final private JwtProvider jwtProvider;
	final private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.jwtProvider = jwtProvider;
		this.passwordEncoder = passwordEncoder;
	}
	private String token = "";
	//회원가입
	public void signUp(MemberDto request, MemberRole roles) {
			Member existMember = memberRepository.findById(request.getId());

			//아아디 존재하면 에러 출력
//			if(existMember != null) {
//				throw new UserDefineException("이미 존재하는 아이디입니다.");
//			}

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

	//로그인
	public String signIn(SignUpDto signUpDto, HttpServletResponse response) {

		Member login = memberRepository.findById(signUpDto.getId());

		//등록되어있지않으면 에러
		if(login.getId() == null) {
			throw new UserDefineException("존재하지 않는 아이디입니다.");
		}

		if(!passwordEncoder.matches(signUpDto.getPw(), login.getPassword())) {
			throw new UserDefineException("비밀번호가 틀렸습니다.");
		}


		Cookie cookie = new Cookie("user", jwtProvider.createToken(login.getId(), login.getRole()));
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);

		return "login success";
		//return jwtProvider.createToken(login.getId(), login.getRole());
	}

	//내정보 수정
	public void updateMember(HttpServletRequest request, MemberDto memberDto) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				token = cookie.getValue();
			}
		}
		String userId = jwtProvider.getUserIdByToken(token); //아이디로 변환

		Member member = memberRepository.findById(userId);

		if(member == null)
			throw new UserDefineException("존재하지 않는 회원입니다");

		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

		member.updateMember(memberDto);

		memberRepository.save(member);

	}

	//정보 삭제 -> 회원 탈퇴
	public void deleteMember(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				token = cookie.getValue();
			}
		}
		String userId = jwtProvider.getUserIdByToken(token);

		Member member = memberRepository.findById(userId);
		
		memberRepository.delete(member);
		
	}


/*

	//회원 정보 열람
	public MemberDto memberInfo(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		String token = cookies[0].getValue(); //user라는 쿠키의 값을 꺼내서
		String userId = jwtProvider.getUserIdByToken(token);
		Member member = memberRepository.findById(userId);

		if(member == null)
			throw new UserDefineException("존재하지 않는 회원입니다");

		return MemberDto.builder()
				.id(member.getId())
				.name(member.getName())
				.affiliation(member.getAffiliation())
				.phone(member.getPhone())
				.sex(member.getSex())
				.age(member.getAge())
				.build();
	}
*/

    public Boolean checkUserID(String userId) {
		Member member = memberRepository.findById(userId);

		//아아디 존재하면 에러 출력
		if (member != null) {
			logger.error("이미 존재하는 아이디입니다!!");
			return false;
		} else {
			logger.info("아이디 중복 체크 완료");
			return true;
		}
	}

    public MemberDto getMemberInfo(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				token = cookie.getValue();
			}
		}

	    String userId = jwtProvider.getUserIdByToken(token); //아이디로 변환

        Member member = memberRepository.findById(userId);

        if(member == null)
            throw new UserDefineException("존재하지 않는 회원입니다");

        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .affiliation(member.getAffiliation())
                .phone(member.getPhone())
                .sex(member.getSex())
                .age(member.getAge())
                .build();
    }

	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				cookie.setValue(null);
				cookie.setMaxAge(0);
			}
		}
		logger.info("Logout Successfully!!");
		response.sendError(HttpServletResponse.SC_OK, "Logout Successfully");
	}
}
