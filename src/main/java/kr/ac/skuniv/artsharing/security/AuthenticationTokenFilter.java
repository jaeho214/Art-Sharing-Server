package kr.ac.skuniv.artsharing.security;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("=================Token을 확인하는 Filter 실행================");

        try{
            String token = jwtProvider.resolveToken(getAsHttpRequest(request));

            log.info(getAsHttpRequest(request).getRequestURL().toString());
            if(token != null && jwtProvider.validateToken(token)){
                SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthenticationByToken(token));
            }
        }catch (JwtException | IllegalArgumentException e){
            log.error("인증이 만료되었거나 올바르지 않은 토큰");
            log.error(e.getMessage());
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.getWriter().write("인증이 만료되었거나 올바르지 않은 토큰");
        }catch (UsernameNotFoundException e){
            log.error("회원을 찾을 수 없음");
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.getWriter().write("회원을 찾을 수 없음");
        }
        chain.doFilter(request, response);
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if(!(request instanceof HttpServletRequest)){
            throw new RuntimeException("Expecting an HTTP request");
        }
        return (HttpServletRequest) request;
    }
}
