package kr.ac.skuniv.artsharing.service.rent;

import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetPagingDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.exception.art.ArtNotFoundException;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.repository.rent.RentRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class RentGetService {

    private final RentRepository rentRepository;
    private final CommonService commonService;
    private final ArtRepository artRepository;

    /**
     * 작가가 본인의 작품의 대여기록을 조회
     * @param cookie : 사용자 정보
     * @param art_id : 작품 번호
     * @param pageNo : 페이지 번호
     * @return : 대여 기록
     */
    @Transactional(readOnly = true)
    public RentGetPagingDto getRentByArt(Cookie cookie, Long art_id, int pageNo) {
        final Member member = commonService.getMemberByCookie(cookie);

        final Art art = artRepository.findById(art_id).orElseThrow(ArtNotFoundException::new);

        commonService.checkAuthority(member.getUserId(), art.getMember().getUserId());

        return RentGetPagingDto.of(rentRepository.findRentByArt(art_id, pageNo));
    }

    /**
     * 고객이 자신의 대여기록을 조회
     * @param cookie : 고객 정보
     * @param pageNo : 페이지 번호
     * @return : 대여 기록
     */
    @Transactional(readOnly = true)
    public RentGetPagingDto getRent(Cookie cookie, int pageNo) {
        final Member member = commonService.getMemberByCookie(cookie);

        return RentGetPagingDto.of(rentRepository.findRentByMember(member.getUserId(), pageNo));
    }

}
