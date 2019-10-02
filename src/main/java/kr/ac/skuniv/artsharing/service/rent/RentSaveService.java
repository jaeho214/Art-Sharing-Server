package kr.ac.skuniv.artsharing.service.rent;

import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.domain.entity.Rent;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.repository.RentRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Service
public class RentSaveService {

    private final CommonService commonService;
    private final RentRepository rentRepository;
    private final ArtRepository artRepository;
    private final MemberRepository memberRepository;

    public RentSaveService(CommonService commonService, RentRepository rentRepository, ArtRepository artRepository, MemberRepository memberRepository) {
        this.commonService = commonService;
        this.rentRepository = rentRepository;
        this.artRepository = artRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 작품 대여
     * @param cookie : 대여인 정보
     * @param rentSaveDto : 대여할 기간 및 비용 등의 데이터
     * @param artNo : 대여할 작품 번호
     * @return : Rent 객체
     */
    public Rent saveRent(Cookie cookie, RentSaveDto rentSaveDto, Long artNo) {
        String userId = commonService.getUserIdByCookie(cookie);

        Member member = memberRepository.findById(userId);
        Art art = artRepository.findById(artNo).get();

        if(art.isRent() == true){
            throw new UserDefineException("작품이 이미 대여중입니다!");
        }

        art.changeRentStatus(true);

        Rent rent = rentSaveDto.toEntity(member, art);

        return rentRepository.save(rent);
    }

    /**
     * 작품 반납
     * @param cookie : 반납할 사용자의 정보
     * @param artNo : 작품 번호
     * @return : Rent 객체
     */
    public Rent returnArt(Cookie cookie, Long artNo) {
        String userId = commonService.getUserIdByCookie(cookie);

        Art art = artRepository.findById(artNo)
                .orElseThrow( () -> new UserDefineException("해당 작품이 없습니다."));

        if(!userId.equals(art.getMember().getId())){
            throw new UserDefineException("작품을 반납할 권한이 없습니다.");
        }

        RentGetDto rentGetDto = rentRepository.findRecentRent(artNo);

        Rent rent = Rent.builder()
                .rentNo(rentGetDto.getRentNo())
                .rentDate(rentGetDto.getRentDate())
                .returnDate(LocalDate.now()) // 반납하면 오늘날짜로 바꾼 후
                .price(rentGetDto.getPrice())
                .member(memberRepository.findById(userId))
                .art(art)
                .build();

        rentRepository.save(rent);

        art.changeRentStatus(false); // 대여여부 false 로 바꿈

        artRepository.save(art);

        return rent;
    }

}
