package kr.ac.skuniv.security;

import kr.ac.skuniv.service.member.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationTokenFilter authenticationTokenFilter;
    private AccessDeniedHandlerCustom accessDeniedHandler;
    private AuthenticationEntryPointCustom authenticationEntryPoint;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder,
                          AuthenticationTokenFilter authenticationTokenFilter, AccessDeniedHandlerCustom accessDeniedHandler,
                          AuthenticationEntryPointCustom authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationTokenFilter = authenticationTokenFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    private static final String[] AUTH_ARR = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html/**",
            "/webjars/**",
            "favicon.ico"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(AUTH_ARR)
                .antMatchers("/artSharing/sign/client")
                .antMatchers("/artSharing/sign/artist")
                .antMatchers("/artSharing/sign/admin")
                .antMatchers(HttpMethod.POST,"/artSharing/sign")
                .antMatchers("/artSharing/art/**")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/artSharing/sign").permitAll()
                        .antMatchers("/artSharing/art").permitAll()
                        .anyRequest().anonymous()
                .and()
                    .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
