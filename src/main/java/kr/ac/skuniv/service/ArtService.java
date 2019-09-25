package kr.ac.skuniv.service;

import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.domain.dto.ReplyDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.ArtImage;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.entity.Reply;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.repository.ArtImageRepository;
import kr.ac.skuniv.repository.ArtRepository;
import kr.ac.skuniv.repository.MemberRepository;
import kr.ac.skuniv.repository.ReplyRepository;
import kr.ac.skuniv.security.JwtProvider;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Service
public class ArtService {

    private static final String IMAGE_PATH = "resources.image-locations";
    private String token = null;


    final private ArtRepository artRepository;
    final private ArtImageRepository artImageRepository;
    final private MemberRepository memberRepository;
    final private ReplyRepository replyRepository;
    final private JwtProvider jwtProvider;
    private Environment environment;

    public ArtService(ArtRepository artRepository, ArtImageRepository artImageRepository,
                      MemberRepository memberRepository, ReplyRepository replyRepository,
                      JwtProvider jwtProvider, Environment environment) {
        this.artImageRepository = artImageRepository;
        this.artRepository = artRepository;
        this.jwtProvider = jwtProvider;
        this.replyRepository = replyRepository;
        this.memberRepository = memberRepository;
        this.environment = environment;
    }


    /**
     * 작품 등록
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param artDto : 등록될 작품의 데이터
     */
    public void saveArt(HttpServletRequest request, ArtDto artDto) {
        String userId = getUserIdByToken(request);
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(userId == null)
            throw new UserDefineException("로그인이 필요합니다.");

        if(userRole.equals(MemberRole.ARTIST)){
            artDto.setUserId(userId);
            Art art = artDto.toEntity(memberRepository.findById(userId));
            artRepository.save(art);
        }else{
            throw new UserDefineException("작품을 등록할 권한이 없습니다.");
        }
    }

    /**
     * 작품 수정
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param artDto : 작품을 수정할 데이터
     */
    public void updateArt(HttpServletRequest request, ArtDto artDto) {
        String userId = getUserIdByToken(request);
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(userId == null)
            throw new UserDefineException("로그인이 필요합니다.");

        Art art = artRepository.findByMemberIdAndId(userId, artDto.getId());

        if(!userId.equals(art.getMember().getId())){
            throw new UserDefineException("작품을 수정할 권한이 없습니다.");
        }

        if(userRole.equals(MemberRole.ARTIST)){
            art.updateArt(artDto);
            artRepository.save(art);
        }else{
            throw new UserDefineException("작품을 수정할 권한이 없습니다.");
        }


    }

    /**
     * 작품 삭제
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param id : 수정할 작품의 번호
     */
    public void deleteArt(HttpServletRequest request, Long id) {
        String userId = getUserIdByToken(request);
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(userId == null)
            throw new UserDefineException("로그인이 필요합니다.");

        Art art = artRepository.findByMemberIdAndId(userId, id);

        if(!userId.equals(art.getMember().getId())){
            throw new UserDefineException("작품을 수정할 권한이 없습니다.");
        }

        if(!userRole.equals(MemberRole.ARTIST)){
            artRepository.delete(art);
        }else{
            throw new UserDefineException("작품을 삭제할 권한이 없습니다.");
        }
    }

    /**
     * 모든 작품 리스트 조회
     * @param pageNum : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    public Page<ArtDto> getAllArts(int pageNum) {
        //작품 리스트 가져오기
        return artRepository.getAllArts(pageNum);
    }

    /**
     * 작가의 작품 리스트 조회(작가의 마이페이지 개념)
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param pageNum : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    public Page<ArtDto> getArtsByUserId(HttpServletRequest request, int pageNum) {
        //작가의 작품 리스트 가져오기
        String userId = getUserIdByToken(request);
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(userId == null) {
            throw new UserDefineException("로그인이 필요합니다.");
        }

        if(!userRole.equals(MemberRole.ARTIST)){
            throw new UserDefineException("일반 고객은 작품을 조회할 수 없습니다.");
        }

        return artRepository.getArtsByUserId(pageNum,userId);
    }


    /**
     * 작품 검색
     * @param keyword : 검색할 내용
     * @param pageNum : 검색이 완료된 화면에서의 페이지 번호(Default=1)
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    public Page<ArtDto> searchArt(String keyword, int pageNum) {
        //검색
        return artRepository.searchArt(keyword,pageNum);
    }

    /**
     * 작품 상세보기
     * @param artNo : 작품 조회 화면에서 클릭이 된 작품의 번호
     * @return : 해당 작품과 댓글들
     */
    public ArtDto getArtDetail(Long artNo) {
        //작품을 눌렀을 때 상세보기
        ArtDto artDto = artRepository.getArtDetail(artNo);

        if(artDto == null){
            throw new UserDefineException("해당 번호의 작품이 없습니다.");
        }

        return artDto;
    }

    /**
     * 댓글 저장
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param replyDto : 저장할 댓글의 데이터
     */
    public void saveReply(HttpServletRequest request, ReplyDto replyDto) {
        String userId = getUserIdByToken(request);
        Member member = memberRepository.findById(userId);
        Art art = artRepository.findById(replyDto.getArtNo()).get();

        if(member == null || art == null){
            throw new UserDefineException("댓글을 저장할 수 없습니다.");
        }

        Reply reply = replyDto.toEntity(member, art);

        replyRepository.save(reply);
    }

    /**
     * 댓글 수정
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param replyDto : 댓글을 수정할 데이터
     */
    public void updateReply(HttpServletRequest request, ReplyDto replyDto) {
        String userId = getUserIdByToken(request);

        Reply reply = replyRepository.findById(replyDto.getReplyNo())
                .orElseThrow(() -> new UserDefineException("해당 댓글을 찾을 수 없습니다."));

        if(!userId.equals(reply.getMember().getId())){
            throw new UserDefineException("해당 댓글을 수정할 권한이 없습니다.");
        }

        reply.updateReply(replyDto);

        replyRepository.save(reply);
    }

    /**
     * 댓글 삭제
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param replyNo : 삭제할 댓글의 번호s
     */
    public void deleteReply(HttpServletRequest request, Long replyNo) {
        String userId = getUserIdByToken(request);

        Reply reply = replyRepository.findById(replyNo)
                .orElseThrow(() -> new UserDefineException("해당 댓글을 찾을 수 없습니다."));

        if(!userId.equals(reply.getMember().getId())){
            throw new UserDefineException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        replyRepository.delete(reply);
    }


    /**
     * token을 통해 userId를 가져오는 메소드
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @return userId
     * @throws UserDefineException
     */
    private String getUserIdByToken(HttpServletRequest request) throws UserDefineException {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    token = cookie.getValue();
                }
            }
        }
        return jwtProvider.getUserIdByToken(token);
    }

    public Art saveArtAndFile(ArtDto artSave, MultipartFile file) throws IOException {
        Art art = Art.builder()
                .artName(artSave.getArtName())
                .explanation(artSave.getExplanation())
                .price(artSave.getPrice())
                .isRent(false)
                .regDate(artSave.getRegDate())
                .member(memberRepository.findById(artSave.getUserId()))
                .build();

        artRepository.save(art);

        ArtImage artImage = saveImage(file, art);


        return art;
    }

    public ArtImage saveImage(MultipartFile file, Art art) throws IOException {

        UUID uid = UUID.randomUUID();
        String fileName = uid + "_" + file.getOriginalFilename();
        String savePath = makePath(environment.getProperty(IMAGE_PATH));
        File destinationFile = new File(environment.getProperty(IMAGE_PATH) + savePath + fileName);

        file.transferTo(destinationFile);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/artSharing/art/image" + art.getArtName())
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
