package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.repository.member.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberGetServiceTest {

    @InjectMocks
    private MemberGetService memberGetService;

    @Mock
    private CommonService commonService;
    @Mock
    private MemberRepository memberRepository;


    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private List<Member> memberListFixture = Arrays.asList(memberFixture);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void getMember() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);

        //when
        MemberGetDto member = memberGetService.getMember(cookie);

        //then
        assertThat(member.getUserId()).isEqualTo(memberFixture.getUserId());
    }

    @Test
    void getArtistList() {
        //given
        given(memberRepository.findByRole(any())).willReturn(memberListFixture);

        //when
        List<MemberGetDto> artistList = memberGetService.getArtistList();

        //then
        assertThat(artistList.size()).isEqualTo(memberListFixture.size());
    }
}