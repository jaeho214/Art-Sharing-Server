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

	/**
	 * 회원가입
	 * @param memberDto : 회원가입을 진행할 데이터
	 * @param roles : 고객과 작가를 구분하기 위한 권한
	 */
	public void signUp(MemberDto memberDto, MemberRole roles) {
			Member member = memberDto.toEntity();
			member.setPassword(passwordEncoder.encode(member.getPassword()));

			if(roles.equals(MemberRole.CLIENT)){
				member.setRole("CLIENT");
			}
			else if(roles.equals(MemberRole.ARTIST)){
				member.setRole("ARTIST");
			}

			memberRepository.save(member);
	}

	/**
	 * 로그인
	 * @param signUpDto : 로그인할 데이터 (ID, PASSWORD)
	 * @param response : 로그인 정보를 cookie에 담기 위한 객체
	 */
	public void signIn(SignUpDto signUpDto, HttpServletResponse response) {

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
	}

	/**
	 * 회원정보 수정
	 * @param request : userId를 조회하기 위한 HttpServletRequest 객체
	 * @param memberDto : 수정할 데이터
	 */
	public void updateMember(HttpServletRequest request, MemberDto memberDto) {
		String userId = getUserIdByToken(request);

		Member member = memberRepository.findById(userId);

		if(member == null)
			throw new UserDefineException("회원 정보를 수정할 수 없습니다.");

		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

		member.updateMember(memberDto);

		memberRepository.save(member);

	}

	/**
	 * 회원 탈퇴
	 * @param request : userId를 조회하기 위한 HttpServletRequest 객체
	 */
	public void deleteMember(HttpServletRequest request) {
		String userId = getUserIdByToken(request);

		Member member = memberRepository.findById(userId);

		if(member == null)
			throw new UserDefineException("회원 정보를 삭제할 수 없습니다.");

		memberRepository.delete(member);
		
	}

	/**
	 * 회원 정보 조회
	 * @param request : userId를 조회하기 위한 HttpServletRequest 객체
	 * @return : 조회한 회원 정보
	 */
	public MemberDto getMemberInfo(HttpServletRequest request) {
		String userId = getUserIdByToken(request);

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

	/**
	 * 로그아웃
	 * @param request : userId를 조회하기 위한 HttpServletRequest 객체
	 */
	public void logout(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				cookie.setValue(null);
				cookie.setMaxAge(0);
			}
		}
		logger.info("Logout Successfully!!");
	}

	/**
	 * ID 중복 체크
	 * @param userId : 중복 체크를 하고자 하는 userId
	 * @return : 중복된 ID가 없으면 True
	 */
    public Boolean checkUserID(String userId) {
		Member member = memberRepository.findById(userId);

		//아아디 존재하면 에러 출력
		if (member != null) {
			throw new UserDefineException("이미 존재하는 아이디입니다!!");
		} else {
			logger.info("아이디 중복 체크 완료");
		}
		return true;
	}


	/**
	 * token을 통해 userId를 가져오는 메소드
	 * @param request : userId를 조회하기 위한 HttpServletRequest 객체
	 * @return userId
	 * @throws UserDefineException
	 */
	private String getUserIdByToken(HttpServletRequest request) throws UserDefineException {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				token = cookie.getValue();
			}
		}
		return jwtProvider.getUserIdByToken(token);
	}
}
