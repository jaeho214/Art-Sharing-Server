package kr.ac.skuniv.artsharing.service.art;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.artImage.ArtImage;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.repository.artImage.ArtImageRepository;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import kr.ac.skuniv.artsharing.service.artImage.ArtImageService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ArtUpdateServiceTest {

    @InjectMocks
    private ArtUpdateService artUpdateService;

    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ArtImageRepository artImageRepository;
    @Mock
    private ArtImageService artImageService;

    private ArtUpdateDto artSaveDtoFixture = new EasyRandom().nextObject(ArtUpdateDto.class);
    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private ArtImage artImageFixture = new EasyRandom().nextObject(ArtImage.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private MockMultipartFile file = new MockMultipartFile("imageFile", "test.txt", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void updateArt() throws IOException {
        //given
        given(objectMapper.readValue(anyString(), any(Class.class))).willReturn(artSaveDtoFixture);
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.findById(anyLong())).willReturn(Optional.ofNullable(artFixture));
        given(artImageRepository.findByArt(any(Art.class))).willReturn(Optional.ofNullable(artImageFixture));
        given(artImageService.saveImage(any(), any(Art.class))).willReturn(artImageFixture);

        //when
        ArtGetDto updatedArt = artUpdateService.updateArt(file, cookie, "json");

        //then
        assertThat(updatedArt.getArtName()).isEqualTo(artFixture.getArtName());
    }
}