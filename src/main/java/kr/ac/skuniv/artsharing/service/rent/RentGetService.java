package kr.ac.skuniv.artsharing.service.rent;

import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.RentRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class RentGetService {

    private final RentRepository rentRepository;
    private final CommonService commonService;

    public RentGetService(RentRepository rentRepository, CommonService commonService) {
        this.rentRepository = rentRepository;
        this.commonService = commonService;
    }

    /**
     * 작가가 본인의 작품의 대여기록을 조회
     * @param cookie : 사용자 정보
     * @param artNo : 작품 번호
     * @param pageNum : 페이지 번호
     * @return : 대여 기록
     */
    public Page<RentGetDto> getArtRentHistory(Cookie cookie, Long artNo, int pageNum) {
        String userId = commonService.getUserIdByCookie(cookie);
        String userRole = commonService.getUserRoleByCookie(cookie);

        if(!userRole.equals(MemberRole.ARTIST.name())){
            throw new UserDefineException("작품의 대여목록을 열람할 권한이 없습니다.");
        }
        Page<RentGetDto> rentPage = rentRepository.findRentByArt(userId, artNo, pageNum);

        if(rentPage == null) {
            throw new UserDefineException("대여 기록이 없습니다.");
        }

        return rentPage;
    }

    /**
     * 고객이 자신의 대여기록을 조회
     * @param cookie : 고객 정보
     * @param pageNum : 페이지 번호
     * @return : 대여 기록
     */
    public Page<RentGetDto> getMemberRentHistory(Cookie cookie, int pageNum) {
        String userId = commonService.getUserIdByCookie(cookie);
        String userRole = commonService.getUserRoleByCookie(cookie);

        if(!userRole.equals(MemberRole.CLIENT.name())){ // FIXME : 고객만 대여할 수 있다고 한다면 냅두고 작가도 대여할 수 있다면 삭제
            throw new UserDefineException("작가는 대여할 수 없습니다.");
        }

        Page<RentGetDto> rentPage = rentRepository.findRentByMember(userId, pageNum);

        if(rentPage == null) {
            throw new UserDefineException("대여한 작품이 없습니다!");
        }

        return rentPage;
    }

}
