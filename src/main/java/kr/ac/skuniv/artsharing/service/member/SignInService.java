package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.SignInDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SignInService {

    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);

    final private MemberRepository memberRepository;
    final private JwtProvider jwtProvider;
    final private PasswordEncoder passwordEncoder;

    public SignInService(MemberRepository memberRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 로그인
     * @param signInDto : 로그인할 데이터 (ID, PASSWORD)
     * @param response : 로그인 정보를 cookie에 담기 위한 객체
     */
    public String signIn(SignInDto signInDto, HttpServletResponse response) {

        Member member = memberRepository.findById(signInDto.getId());

        //등록되어있지않으면 에러
        if(member.getId() == null) {
            throw new UserDefineException("존재하지 않는 아이디입니다.");
        }

        if(!passwordEncoder.matches(signInDto.getPw(), member.getPassword())) {
            throw new UserDefineException("비밀번호가 틀렸습니다.");
        }

        Cookie cookie = new Cookie("user", jwtProvider.createToken(member.getId(), member.getRole()));
        cookie.setMaxAge(60*60*24);
        cookie.setPath("/");
        response.addCookie(cookie);

        return jwtProvider.createToken(member.getId(), member.getRole());
    }

    /**
     * 로그아웃
     * @param response : 로그인 정보를 cookie에서 삭제하기 위한 객체
     */
    public void logout(HttpServletResponse response){
        Cookie cookie = new Cookie("user" , null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        logger.info("Logout Successfully!!");
    }
}
