package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.entity.ArtImage;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ArtImageRepository;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ArtGetService {

    private final CommonService commonService;
    private final ArtRepository artRepository;
    private final ArtImageRepository artImageRepository;

    public ArtGetService(CommonService commonService, ArtRepository artRepository, ArtImageRepository artImageRepository) {
        this.commonService = commonService;
        this.artRepository = artRepository;
        this.artImageRepository = artImageRepository;
    }

    public byte[] getImageResource(Long artNo) {
        ArtImage artImage = artImageRepository.findByArt(artRepository.findById(artNo).get()).get();
        byte[] result = null;
        try{
            File file = new File(artImage.getImagePath());

            InputStream in = new FileInputStream(file);
            result = IOUtils.toByteArray(in);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 모든 작품 리스트 조회
     * @param pageNum : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    public Page<ArtGetDto> getAllArts(int pageNum) {
        //작품 리스트 가져오기
        return artRepository.getAllArts(pageNum);
    }

    /**
     * 로그인되어 있는 작가의 작품 리스트 조회(작가의 마이페이지 개념)
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param pageNum : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    public Page<ArtGetDto> getArtsByUserId(Cookie cookie, int pageNum) {
        //작가의 작품 리스트 가져오기
        String userId = commonService.getUserIdByCookie(cookie);
        String userRole = commonService.getUserRoleByCookie(cookie);

        if(userId == null) {
            throw new UserDefineException("로그인이 필요합니다.");
        }

        if(!userRole.equals(MemberRole.ARTIST)){
            throw new UserDefineException("일반 고객은 작품을 조회할 수 없습니다.");
        }

        return artRepository.getArtsByUserId(pageNum,userId);
    }

    /**
     * 작가 보기에서 작가 이름을 눌렀을 때 작가의 작품 리스트 출력
     * @param artistId : 작가 ID
     * @param pageNum : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    public Page<ArtGetDto> getArtsByArtist(String artistId, int pageNum) {
        return artRepository.getArtsByUserId(pageNum, artistId);
    }


    /**
     * 작품 검색
     * @param keyword : 검색할 내용
     * @param pageNum : 검색이 완료된 화면에서의 페이지 번호(Default=1)
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    public Page<ArtGetDto> searchArt(String keyword, int pageNum) {
        //검색
        return artRepository.searchArt(keyword,pageNum);
    }

    /**
     * 작품 상세보기
     * @param artNo : 작품 조회 화면에서 클릭이 된 작품의 번호
     * @return : 해당 작품과 댓글들
     */
    public ArtGetDetailDto getArtDetail(Long artNo) {
        //작품을 눌렀을 때 상세보기
        ArtGetDetailDto artDto = artRepository.getArtDetail(artNo);

        if(artDto == null){
            throw new UserDefineException("해당 번호의 작품이 없습니다.");
        }

        return artDto;
    }


}
