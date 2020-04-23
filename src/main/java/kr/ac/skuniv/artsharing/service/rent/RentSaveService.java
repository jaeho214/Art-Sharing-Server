package kr.ac.skuniv.artsharing.service.rent;

import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.rent.Rent;
import kr.ac.skuniv.artsharing.exception.art.ArtNotFoundException;
import kr.ac.skuniv.artsharing.exception.rent.RentNotFoundException;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.repository.rent.RentRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class RentSaveService {

    private final CommonService commonService;
    private final RentRepository rentRepository;
    private final ArtRepository artRepository;

    /**
     * 작품 대여
     * @param cookie : 대여인 정보
     * @param rentSaveDto : 대여할 기간 및 비용 등의 데이터
     * @param art_id : 대여할 작품 번호
     * @return : Rent 객체
     */
    public RentGetDto saveRent(Cookie cookie, RentSaveDto rentSaveDto, Long art_id) {
        final Member member = commonService.getMemberByCookie(cookie);

        final Art art = artRepository.findById(art_id)
                .orElseThrow(ArtNotFoundException::new);

        art.changeRentStatus(true);

        return RentGetDto.of(rentRepository.save(rentSaveDto.of(member, art)));
    }

    /**
     * 작품 반납
     * @param cookie : 반납할 사용자의 정보
     * @param rent_id : 작품 번호
     * @return : Rent 객체
     */
    public RentGetDto returnArt(Cookie cookie, Long rent_id) {
        final Member member = commonService.getMemberByCookie(cookie);

        final Rent rent = rentRepository.findById(rent_id).orElseThrow(RentNotFoundException::new);

        commonService.checkAuthority(member.getUserId(), rent.getMember().getUserId());

        rent.updateReturnDate(LocalDate.now());

        return RentGetDto.of(rent);
    }

}
