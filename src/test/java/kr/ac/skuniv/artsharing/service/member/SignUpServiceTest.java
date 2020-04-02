package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.dto.member.SignUpDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

    @InjectMocks
    private SignUpService signUpService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private SignUpDto signUpDtoFixture = new EasyRandom().nextObject(SignUpDto.class);
    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private String password = "password";

    @Test
    void signUp() {
        //given
        given(memberRepository.save(any(Member.class))).willReturn(memberFixture);
        given(passwordEncoder.encode(anyString())).willReturn(password);

        //when
        MemberGetDto savedMember = signUpService.signUp(signUpDtoFixture);

        //then
        assertThat(savedMember.getUserId()).isEqualTo(memberFixture.getUserId());

    }

    @Test
    void checkUserId() {
        //given
        given(memberRepository.existsByUserId(anyString())).willReturn(true);

        //when
        boolean check = signUpService.checkUserId("userId");

        //then
        assertThat(check).isTrue();
    }
}