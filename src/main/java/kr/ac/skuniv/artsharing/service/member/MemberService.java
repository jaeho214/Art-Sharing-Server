package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
	private String token = null;






	/**
	 * token을 통해 userId를 가져오는 메소드
	 * @param request : userId를 조회하기 위한 HttpServletRequest 객체
	 * @return userId
	 * @throws UserDefineException
	 */
	private String getUserIdByToken(HttpServletRequest request) throws UserDefineException {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user")) {
					token = cookie.getValue();
				}
			}
		}
		return jwtProvider.getUserIdByToken(token);
	}
}
