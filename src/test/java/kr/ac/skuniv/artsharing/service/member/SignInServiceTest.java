package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.SignInDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.repository.member.MemberRepository;
import kr.ac.skuniv.artsharing.security.JwtProvider;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class SignInServiceTest {

    @InjectMocks
    private SignInService signInService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private SignInDto signInDtoFixture = new EasyRandom().nextObject(SignInDto.class);

    private MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    private String token = "token";

    @Test
    void signIn() {
        //given
        given(memberRepository.findByUserId(anyString())).willReturn(java.util.Optional.ofNullable(memberFixture));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtProvider.createToken(anyString(), any())).willReturn(token);

        //when
        String token = signInService.signIn(signInDtoFixture, httpServletResponse);

        //then
        assertThat(token).isNotNull();
    }

}