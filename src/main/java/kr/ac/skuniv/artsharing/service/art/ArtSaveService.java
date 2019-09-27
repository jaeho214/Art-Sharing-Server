package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.ArtImage;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ArtImageRepository;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Service
public class ArtSaveService {

    private static final String IMAGE_PATH = "resources.image-locations";

    private final CommonService commonService;
    private final ArtRepository artRepository;
    private final MemberRepository memberRepository;
    private final ArtImageRepository artImageRepository;
    private Environment environment;

    public ArtSaveService(CommonService commonService, ArtRepository artRepository, MemberRepository memberRepository,
                          ArtImageRepository artImageRepository, Environment environment) {
        this.commonService = commonService;
        this.artRepository = artRepository;
        this.memberRepository = memberRepository;
        this.artImageRepository = artImageRepository;
        this.environment = environment;
    }

    /**
     * 작품 등록
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param artSaveDto : 등록될 작품의 데이터
     */
    public void saveArt(HttpServletRequest request, ArtSaveDto artSaveDto) {
        String userId = commonService.getUserIdByToken(request);
        String userRole = commonService.getUserRoleByToken(request);

        if(userId == null)
            throw new UserDefineException("로그인이 필요합니다.");

        if(userRole.equals(MemberRole.ARTIST)){
            //artSaveDto.setUserId(userId);
            Art art = artSaveDto.toEntity(memberRepository.findById(userId));
            artRepository.save(art);
        }else{
            throw new UserDefineException("작품을 등록할 권한이 없습니다.");
        }
    }

    public Art saveArtAndFile(HttpServletRequest request, ArtSaveDto artSaveDto, MultipartFile file) throws IOException {
        String userId = commonService.getUserIdByToken(request);
        String userRole = commonService.getUserRoleByToken(request);

        if(userId == null)
            throw new UserDefineException("로그인이 필요합니다.");

        if(userRole.equals(MemberRole.CLIENT)){
            throw new UserDefineException("작품을 등록할 권한이 없습니다.");
        }

        Art art = Art.builder()
                .artName(artSaveDto.getArtName())
                .explanation(artSaveDto.getExplanation())
                .price(artSaveDto.getPrice())
                .isRent(false)
                .member(memberRepository.findById(userId))
                .build();

        artRepository.save(art);

        ArtImage artImage = saveImage(file, art);

        artImageRepository.save(artImage);


        return art;
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
