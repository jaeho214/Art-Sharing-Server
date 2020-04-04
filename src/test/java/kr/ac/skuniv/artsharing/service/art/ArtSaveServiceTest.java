package kr.ac.skuniv.artsharing.service.art;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.ArtImage;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.repository.ArtImageRepository;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import kr.ac.skuniv.artsharing.service.artImage.ArtImageService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.http.Cookie;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArtSaveServiceTest {

    @InjectMocks
    private ArtSaveService artSaveService;

    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;
    @Mock
    private ArtImageRepository artImageRepository;
    @Mock
    private ArtImageService artImageService;
    @Mock
    private ObjectMapper objectMapper;


    private ArtSaveDto artSaveDtoFixture = new EasyRandom().nextObject(ArtSaveDto.class);
    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private ArtImage artImageFixture = new EasyRandom().nextObject(ArtImage.class);
    private Cookie cookie = new Cookie("user", "token");
    private MockMultipartFile file = new MockMultipartFile("imageFile", "test.txt", null, "test data".getBytes());
    @Test
    void saveArt() throws IOException {
        //given
        given(objectMapper.readValue(anyString(), any(Class.class))).willReturn(artSaveDtoFixture);
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.save(any(Art.class))).willReturn(artFixture);
        given(artImageRepository.save(any(ArtImage.class))).willReturn(artImageFixture);
        given(artImageService.saveImage(any(), any(Art.class))).willReturn(artImageFixture);

        //when
        ArtGetDto savedArt = artSaveService.saveArt(cookie, "json", file);

        //then
        assertThat(savedArt.getArtName()).isEqualTo(artFixture.getArtName());
    }
}