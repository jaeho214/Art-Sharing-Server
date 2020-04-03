package kr.ac.skuniv.artsharing.service.art;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.ArtImage;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ArtImageRepository;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import kr.ac.skuniv.artsharing.service.artImage.ArtImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArtSaveService {

    private final CommonService commonService;
    private final ArtRepository artRepository;
    private final ArtImageRepository artImageRepository;
    private final ArtImageService artImageService;
    private final ObjectMapper objectMapper;

    public ArtGetDto saveArt(Cookie cookie, String json, MultipartFile file) {
        try {
            ArtSaveDto artSaveDto = objectMapper.readValue(json, ArtSaveDto.class);

            if (file == null)
                throw new UserDefineException("이미지를 등록해주세요!");

            Member member = commonService.getMemberByCookie(cookie);

            commonService.checkRole(member.getRole());

            Art savedArt = artRepository.save(artSaveDto.of(member));

            ArtImage savedImage = artImageRepository.save(artImageService.saveImage(file, savedArt));

            return ArtGetDto.of(savedArt, savedImage);

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return null;

    }


}
