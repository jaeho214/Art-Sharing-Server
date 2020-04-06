package kr.ac.skuniv.artsharing.service;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
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
                    .orElseThrow(()-> new UserDefineException("회원을 찾을 수 없습니다."));
            return member;
        }catch (Exception e){
            throw new UserDefineException("로그인이 필요합니다.");
        }
    }

    public void checkAuthority(String userId, String writer){
        if(userId.equals(writer))
            return;
        throw new UserDefineException("해당 작업을 할 수 있는 권한이 없습니다.");
    }

    public void checkRole(MemberRole role){
        if (!(role.equals(MemberRole.ARTIST))) {
            throw new UserDefineException("작품을 등록할 권한이 없습니다.");
        }
    }


}
