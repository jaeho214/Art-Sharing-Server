package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArtSaveServiceTest {

    @InjectMocks
    private ArtSaveService artSaveService;

    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;

    private ArtSaveDto artSaveDtoFixture = new EasyRandom().nextObject(ArtSaveDto.class);
    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private Cookie cookie = new Cookie("user", "token");
    @Test
    void saveArt() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.save(any(Art.class))).willReturn(artFixture);

        //when
        ArtGetDto savedArt = artSaveService.saveArt(cookie, artSaveDtoFixture);

        //then
        assertThat(savedArt.getArtName()).isEqualTo(artFixture.getArtName());
    }
}