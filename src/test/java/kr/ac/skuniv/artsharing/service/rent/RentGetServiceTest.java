package kr.ac.skuniv.artsharing.service.rent;

import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetPagingDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.repository.rent.RentRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RentGetServiceTest {

    @InjectMocks
    private RentGetService rentGetService;
    @Mock
    private RentRepository rentRepository;
    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private Page<RentGetDto> rentPage = Mockito.mock(Page.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void getRentByArt() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.findById(anyLong())).willReturn(Optional.ofNullable(artFixture));
        given(rentRepository.findRentByArt(anyLong(), anyInt())).willReturn(rentPage);

        //when
        RentGetPagingDto rent = rentGetService.getRentByArt(cookie, 1L, 1);

        //then
        assertThat(rent.getRentGetDtoList()).isNotNull();
    }

    @Test
    void getRent() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(rentRepository.findRentByMember(anyString(), anyInt())).willReturn(rentPage);

        //when
        RentGetPagingDto rent = rentGetService.getRent(cookie, 1);

        //then
        assertThat(rent.getRentGetDtoList()).isNotNull();
    }
}