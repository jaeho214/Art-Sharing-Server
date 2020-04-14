package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class ArtUpdateServiceTest {

    @InjectMocks
    private ArtUpdateService artUpdateService;

    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;

    private ArtUpdateDto artUpdateDtoFixture = new EasyRandom().nextObject(ArtUpdateDto.class);
    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void updateArt() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.findById(anyLong())).willReturn(Optional.ofNullable(artFixture));

        //when
        ArtGetDto updatedArt = artUpdateService.updateArt(cookie, artUpdateDtoFixture);

        //then
        assertThat(updatedArt.getArtName()).isEqualTo(artFixture.getArtName());
    }
}