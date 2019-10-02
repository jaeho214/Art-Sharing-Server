package kr.ac.skuniv.artsharing.service;

import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.security.JwtProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class CommonService {

    private final JwtProvider jwtProvider;
    private String token = "";

    public CommonService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * 고객의 아이디를 얻어내는 메소드
     * @param cookie : 고객 정보가 담긴 토큰이 있는 쿠키
     * @return : 사용자 아이디 String
     */
    public String getUserIdByCookie(Cookie cookie){
        if(cookie == null){
            throw new UserDefineException("로그인을 하세요!!");
        }
        String token = cookie.getValue();
        return jwtProvider.getUserIdByToken(token);
    }

    /**
     * 고객의 권한을 얻어내는 메소드
     * @param cookie : 고객 정보가 담긴 토큰이 있는 쿠키
     * @return : 권한 String
     */
    public String getUserRoleByCookie(Cookie cookie){
        if(cookie == null){
            throw new UserDefineException("로그인을 하세요!!");
        }
        String token = cookie.getValue();
        return jwtProvider.getUserRoleByToken(token);
    }

//    public String getUserIdByToken(HttpServletRequest request) throws UserDefineException {
//        Cookie[] cookies = request.getCookies();
//        if(cookies == null){
//            throw new UserDefineException("로그인을 하세요!!");
//        } else {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("user")) {
//                    token = cookie.getValue();
//                }
//            }
//        }
//        return jwtProvider.getUserIdByToken(token);
//    }
//
//    public String getUserRoleByToken(HttpServletRequest request) throws UserDefineException {
//        Cookie[] cookies = request.getCookies();
//        if(cookies == null){
//            throw new UserDefineException("로그인을 하세요!!");
//        } else {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("user")) {
//                    token = cookie.getValue();
//                }
//            }
//        }
//        return jwtProvider.getUserRoleByToken(token);
//    }


}
