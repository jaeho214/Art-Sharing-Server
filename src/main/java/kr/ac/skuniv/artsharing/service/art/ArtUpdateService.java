package kr.ac.skuniv.artsharing.service.art;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.artImage.ArtImage;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.exception.art.ArtNotFoundException;
import kr.ac.skuniv.artsharing.exception.artImage.ArtImageNotFoundException;
import kr.ac.skuniv.artsharing.repository.artImage.ArtImageRepository;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import kr.ac.skuniv.artsharing.service.artImage.ArtImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtUpdateService {

    private final CommonService commonService;
    private final ArtRepository artRepository;
    private final ArtImageService artImageService;
    private final ArtImageRepository artImageRepository;
    private final ObjectMapper objectMapper;


    /**
     * 작품 수정
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param json : 작품을 수정할 데이터
     */
    public ArtGetDto updateArt(MultipartFile imageFile, Cookie cookie, String json) throws IOException {
        ArtUpdateDto artUpdateDto = objectMapper.readValue(json, ArtUpdateDto.class);
        Member member = commonService.getMemberByCookie(cookie);

        Art art = artRepository.findById(artUpdateDto.getId())
                .orElseThrow(ArtNotFoundException::new);

        commonService.checkAuthority(member.getUserId(), art.getMember().getUserId());

        ArtImage updatedImage = null;
        if(imageFile.getContentType() != null){
            updatedImage = updateArtImage(imageFile, art);
        }
        art.updateArt(artUpdateDto);


        if(updatedImage == null)
            return ArtGetDto.of(art);

        return ArtGetDto.of(art, updatedImage);
    }


    /**
     * 작품의 이미지를 수정하는 메소드
     * @param imageFile : 수정할 이미지 파일
     * @param art : 수정할 작품
     * @throws IOException
     */
    private ArtImage updateArtImage(MultipartFile imageFile, Art art) {
        ArtImage artImage = artImageRepository.findByArt(art)
                .orElseThrow(ArtImageNotFoundException::new);

        ArtImage updateArtImage = artImageService.saveImage(imageFile, art);

        artImage.updateArtImage(updateArtImage);
        return artImage;
    }


}
