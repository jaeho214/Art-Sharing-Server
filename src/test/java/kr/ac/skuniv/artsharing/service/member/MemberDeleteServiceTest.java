package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberDeleteServiceTest {

    @InjectMocks
    private MemberDeleteService memberDeleteService;

    @Mock
    private CommonService commonService;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Cookie cookie = new Cookie("user", "token");
    @Test
    void deleteMember() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);

        //when
        ResponseEntity responseEntity = memberDeleteService.deleteMember(cookie);

        //then
        assertThat(responseEntity).isEqualTo(ResponseEntity.noContent().build());
    }
}