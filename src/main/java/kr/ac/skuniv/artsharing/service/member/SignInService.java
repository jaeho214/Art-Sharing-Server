package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.SignInDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class SignInService {


    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    /**
     * 로그인
     * @param signInDto : 로그인할 데이터 (ID, PASSWORD)
     * @param response : 로그인 정보를 cookie에 담기 위한 객체
     */
    public String signIn(SignInDto signInDto, HttpServletResponse response) {

        Member member = memberRepository.findByUserId(signInDto.getUserId())
                .orElseThrow(()->new UserDefineException("존재하지 않는 아이디입니다."));

        if(!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())) {
            throw new UserDefineException("비밀번호가 틀렸습니다.");
        }

        Cookie cookie = new Cookie("user", jwtProvider.createToken(member.getUserId(), member.getRole()));
        cookie.setMaxAge(60*60*24);
        cookie.setPath("/");
        response.addCookie(cookie);

        return member.getName();
    }

    /**
     * 로그아웃
     * @param response : 로그인 정보를 cookie에서 삭제하기 위한 객체
     */
    public ResponseEntity logout(HttpServletResponse response){
        Cookie cookie = new Cookie("user" , null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }
}
