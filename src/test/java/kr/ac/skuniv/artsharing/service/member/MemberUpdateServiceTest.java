package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberUpdateServiceTest {

    @InjectMocks
    private MemberUpdateService memberUpdateService;

    @Mock
    private CommonService commonService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private MemberUpdateDto memberUpdateDtoFixture = new EasyRandom().nextObject(MemberUpdateDto.class);
    private String password = "password";
    private Cookie cookie = new Cookie("user", "token");
    @Test
    void updateMember() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(passwordEncoder.encode(anyString())).willReturn(password);

        //when
        MemberGetDto updatedMember = memberUpdateService.updateMember(cookie, memberUpdateDtoFixture);

        //then
        assertThat(updatedMember.getUserId()).isEqualTo(memberFixture.getUserId());
    }
}