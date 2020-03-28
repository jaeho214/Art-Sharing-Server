package kr.ac.skuniv.artsharing.service.art;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.ArtImage;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ArtImageRepository;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtSaveService {

    private static final String IMAGE_PATH = "resources.image-locations";

    private final CommonService commonService;
    private final ArtRepository artRepository;
    private final ArtImageRepository artImageRepository;
    private final Environment environment;
    private final ObjectMapper objectMapper;


    public ArtGetDto saveArtAndFile(Cookie cookie, String json, MultipartFile file) throws IOException {
        ArtSaveDto artSaveDto = objectMapper.readValue(json, ArtSaveDto.class);

        if(file == null)
            throw new UserDefineException("이미지를 등록해주세요!");

        Member member = commonService.getMemberByCookie(cookie);

        if(!(member.getRole().equals(MemberRole.ARTIST.name()))){
            throw new UserDefineException("작품을 등록할 권한이 없습니다.");
        }

        Art savedArt = artRepository.save(artSaveDto.of(member));

        ArtImage artImage = saveImage(file, savedArt);

        ArtImage savedImage = artImageRepository.save(artImage);


        return ArtGetDto.of(savedArt, savedImage);
    }

    public ArtImage saveImage(MultipartFile file, Art art) throws IOException {

        UUID uid = UUID.randomUUID();
        String fileName = uid + "_" + file.getOriginalFilename();
        String savePath = makePath(environment.getProperty(IMAGE_PATH));
        File destinationFile = new File(environment.getProperty(IMAGE_PATH) + savePath, fileName);

        file.transferTo(destinationFile);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/artSharing/art/image/" + art.getId())
                .toUriString();

        return ArtImage.builder()
                .imageName(file.getOriginalFilename())
                .imageSize(file.getSize())
                .imageType(file.getContentType())
                .imageUrl(imageUrl)
                .imagePath(environment.getProperty(IMAGE_PATH) + savePath + File.separator + fileName)
                .art(art)
                .build();
    }

    private String makePath(String uploadPath) {

        Calendar calendar = Calendar.getInstance();

        String yearPath = File.separator + calendar.get(Calendar.YEAR);
        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH)+1);
        String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        return datePath;

    }

    private void makeDir(String uploadPath, String... paths) {

        if(new File(uploadPath + paths[paths.length - 1]).exists()) {
            return;
        }

        for( String path : paths){
            File dirPath = new File(uploadPath + path);

            if(!dirPath.exists()){
                dirPath.mkdir();
            }
        }
    }
}
