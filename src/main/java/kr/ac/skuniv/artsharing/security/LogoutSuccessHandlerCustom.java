package kr.ac.skuniv.artsharing.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutSuccessHandlerCustom implements LogoutSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerCustom.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("Logout Successfully!!");
        response.sendError(HttpServletResponse.SC_OK);
    }
}
