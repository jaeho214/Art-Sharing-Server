package kr.ac.skuniv.artsharing.service;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.exception.member.CookieNotFoundException;
import kr.ac.skuniv.artsharing.exception.member.MemberNotFoundException;
import kr.ac.skuniv.artsharing.repository.member.MemberRepository;
import kr.ac.skuniv.artsharing.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;


    /**
     * 고객의 아이디를 얻어내는 메소드
     * @param cookie : 고객 정보가 담긴 토큰이 있는 쿠키
     * @return : 사용자 아이디 String
     */
    public Member getMemberByCookie(Cookie cookie){
        try{
            String userID = jwtProvider.getUserIdByToken(cookie.getValue());
            Member member =  memberRepository.findByUserId(userID)
                    .orElseThrow(MemberNotFoundException::new);
            return member;
        }catch (Exception e){
            throw new CookieNotFoundException();
        }
    }

    public void checkAuthority(String userId, String writer){
        if(userId.equals(writer))
            return;
        throw new BusinessLogicException(ErrorCodeType.USER_UNAUTHORIZED);
    }

    public void checkRole(MemberRole role){
        if (!(role.equals(MemberRole.ARTIST))) {
            throw new BusinessLogicException(ErrorCodeType.USER_UNAUTHORIZED);
        }
    }


}
