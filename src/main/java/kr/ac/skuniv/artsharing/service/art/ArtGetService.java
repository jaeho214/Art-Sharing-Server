package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetPagingDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.artImage.ArtImage;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.exception.art.ArtNotFoundException;
import kr.ac.skuniv.artsharing.exception.artImage.ArtImageNotFoundException;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.repository.artImage.ArtImageRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ArtGetService {

    private final CommonService commonService;
    private final ArtRepository artRepository;
    private final ArtImageRepository artImageRepository;

    /**
     * 이미지를 가져오는 메소드
     * @param art_id : 작품 번호
     * @return : 작품의 바이트
     */
    public byte[] getImageResource(Long art_id) {
        Art art = artRepository.findById(art_id)
                .orElseThrow(ArtNotFoundException::new);
        ArtImage artImage = artImageRepository.findByArt(art)
                .orElseThrow(ArtImageNotFoundException::new);

        try{
            File file = new File(artImage.getImagePath());

            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 모든 작품 리스트 조회
     * @param pageNo : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    @Transactional(readOnly = true)
    public ArtGetPagingDto getAllArts(int pageNo) {
        //작품 리스트 가져오기
        return ArtGetPagingDto.of(artRepository.getAllArts(pageNo));
    }

    /**
     * 로그인되어 있는 작가의 작품 리스트 조회(작가의 마이페이지 개념)
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param pageNum : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    @Transactional(readOnly = true)
    public ArtGetPagingDto getArtsByUserId(Cookie cookie, int pageNum) {
        //작가의 작품 리스트 가져오기
        Member member = commonService.getMemberByCookie(cookie);

        commonService.checkRole(member.getRole());

        return ArtGetPagingDto.of(artRepository.getArtsByUserId(member.getUserId(), pageNum));
    }

    /**
     * 작가 보기에서 작가 이름을 눌렀을 때 작가의 작품 리스트 출력
     * @param artistUserId : 작가 ID
     * @param pageNum : 리스트 화면에서의 페이지 번호
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    @Transactional(readOnly = true)
    public ArtGetPagingDto getArtsByArtist(String artistUserId, int pageNum) {
        return ArtGetPagingDto.of(artRepository.getArtsByUserId(artistUserId, pageNum));
    }


    /**
     * 작품 검색
     * @param keyword : 검색할 내용
     * @param pageNum : 검색이 완료된 화면에서의 페이지 번호(Default=1)
     * @return : 페이지 번호에 맞게 해당 작품들 반환
     */
    @Transactional(readOnly = true)
    public ArtGetPagingDto searchArtByKeyword(String keyword, int pageNum) {
        //검색
        return ArtGetPagingDto.of(artRepository.searchArtByKeyword(keyword, pageNum));
    }

    /**
     * 작품 상세보기
     * @param art_id : 작품 조회 화면에서 클릭이 된 작품의 번호
     * @return : 해당 작품과 댓글들
     */
    public ArtGetDetailDto getArtDetail(Long art_id) {
        //작품을 눌렀을 때 상세보기
        return artRepository.getArtDetail(art_id)
                .orElseThrow(ArtNotFoundException::new);
    }


    public ArtGetPagingDto getArtByRent(int pageNo) {

        return ArtGetPagingDto.of(artRepository.getByRent(pageNo));
    }
}
