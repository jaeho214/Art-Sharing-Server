package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.exception.art.ArtNotFoundException;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtDeleteService {

    private final CommonService commonService;
    private final ArtRepository artRepository;


    /**
     * 작품 삭제
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param id : 수정할 작품의 번호
     */
    public ResponseEntity deleteArt(Cookie cookie, Long id) {
        final Member member = commonService.getMemberByCookie(cookie);

        final Art art = artRepository.findById(id).orElseThrow(ArtNotFoundException::new);
        
        commonService.checkAuthority(member.getUserId(), art.getMember().getUserId());

        //artRepository.delete(art);
        art.delete();

        return ResponseEntity.noContent().build();
    }
}
