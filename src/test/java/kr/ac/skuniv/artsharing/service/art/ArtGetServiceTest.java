package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetPagingDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class ArtGetServiceTest {

    @InjectMocks
    private ArtGetService artGetService;
    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private ArtGetDetailDto artGetDetailDtoFixture = new EasyRandom().nextObject(ArtGetDetailDto.class);
    private Page<ArtGetDto> artPageFixture = Mockito.mock(Page.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void getAllArts() {
        //given
        given(artRepository.getAllArts(anyInt())).willReturn(artPageFixture);

        //when
        ArtGetPagingDto arts = artGetService.getAllArts(1);

        //then
        assertThat(arts.getArtPages().size()).isEqualTo(artPageFixture.getSize());
    }

    @Test
    void getArtsByUserId() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.getArtsByUserId(anyString(), anyInt())).willReturn(artPageFixture);

        //when
        ArtGetPagingDto arts = artGetService.getArtsByUserId(cookie, 1);

        //then
        assertThat(arts.getArtPages().size()).isEqualTo(artPageFixture.getSize());
    }

    @Test
    void getArtsByArtist() {
        //given
        given(artRepository.getArtsByUserId(anyString(), anyInt())).willReturn(artPageFixture);

        //when
        ArtGetPagingDto arts = artGetService.getArtsByArtist("userId", 1);

        //then
        assertThat(arts.getArtPages().size()).isEqualTo(artPageFixture.getSize());
    }

    @Test
    void searchArtByKeyword() {
        //given
        given(artRepository.searchArtByKeyword(anyString(), anyInt())).willReturn(artPageFixture);

        //when
        ArtGetPagingDto arts = artGetService.searchArtByKeyword("userId", 1);

        //then
        assertThat(arts.getArtPages().size()).isEqualTo(artPageFixture.getSize());
    }

    @Test
    void getArtDetail() {
        //given
        given(artRepository.getArtDetail(anyLong())).willReturn(Optional.ofNullable(artGetDetailDtoFixture));

        //when
        ArtGetDetailDto arts = artGetService.getArtDetail(1L);

        //then
        assertThat(arts.getArtName()).isEqualTo(artGetDetailDtoFixture.getArtName());
    }

    @Test
    void getArtByRent(){
        //given
        given(artRepository.getByRent(anyInt())).willReturn(artPageFixture);

        //when
        ArtGetPagingDto art = artGetService.getArtByRent(1);

        //then
        assertThat(art.getArtPages()).isNotNull();
    }
}