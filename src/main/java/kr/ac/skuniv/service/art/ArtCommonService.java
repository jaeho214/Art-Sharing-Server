package kr.ac.skuniv.service.art;

import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.security.JwtProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class ArtCommonService {

    private final JwtProvider jwtProvider;
    private String token = "";

    public ArtCommonService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * token을 통해 userId를 가져오는 메소드
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @return userId
     * @throws UserDefineException
     */
    public String getUserIdByToken(HttpServletRequest request) throws UserDefineException {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            throw new UserDefineException("로그인을 하세요!!");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    token = cookie.getValue();
                }
            }
        }
        return jwtProvider.getUserIdByToken(token);
    }

    public String getUserRoleByToken(HttpServletRequest request) throws UserDefineException {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            throw new UserDefineException("로그인을 하세요!!");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    token = cookie.getValue();
                }
            }
        }
        return jwtProvider.getUserRoleByToken(token);
    }
}
