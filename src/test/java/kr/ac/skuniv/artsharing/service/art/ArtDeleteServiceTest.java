package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArtDeleteServiceTest {

    @InjectMocks
    private ArtDeleteService artDeleteService;

    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private Cookie cookie = new Cookie("user", "token");
    @Test
    void deleteArt() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.findById(anyLong())).willReturn(Optional.ofNullable(artFixture));

        //then
        ResponseEntity responseEntity = artDeleteService.deleteArt(cookie, 1L);

        //when
        assertThat(responseEntity).isEqualTo(ResponseEntity.noContent().build());
    }
}