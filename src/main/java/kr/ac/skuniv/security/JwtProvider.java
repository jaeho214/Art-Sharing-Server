package kr.ac.skuniv.security;

import io.jsonwebtoken.*;
import kr.ac.skuniv.service.member.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private final long validityMilliseconds = 360000000;
    private String secretKey = "ARTSHARINGDEVELOP";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claims.getBody().getExpiration().after(new Date());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    bearerToken = cookie.getValue();
                }
            }
        }
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

    public Authentication getAuthenticationByToken(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserIdByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserIdByToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(resolveToken(token)).getBody().getSubject();
    }

    public String getUserRoleByToken(String token) {
        String roles = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(resolveToken(token)).getBody().get("roles");
        return roles;
    }
}