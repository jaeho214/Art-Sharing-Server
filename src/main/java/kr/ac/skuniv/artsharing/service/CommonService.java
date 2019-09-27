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

    public String getUserIdByCookie(Cookie cookie){
        if(cookie == null){
            throw new UserDefineException("로그인을 하세요!!");
        }
        String token = cookie.getValue();
        return jwtProvider.getUserIdByToken(token);
    }

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
