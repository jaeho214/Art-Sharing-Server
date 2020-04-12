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
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    private final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private final String NUMBER = "0123456789";
    private final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
    private final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;


    /**
     * 고객의 아이디를 얻어내는 메소드
     * @param cookie : 고객 정보가 담긴 토큰이 있는 쿠키
     * @return : 사용자 아이디 String
     */
    public Member getMemberByCookie(Cookie cookie){
        try{
            return memberRepository.
                    findByUserId(jwtProvider.getUserIdByToken(cookie.getValue()))
                    .orElseThrow(MemberNotFoundException::new);
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

    public String generateRandomPassword(int length) {
        if (length < 1)
            throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }

    private static String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        return letters.stream().collect(Collectors.joining());
    }


}
