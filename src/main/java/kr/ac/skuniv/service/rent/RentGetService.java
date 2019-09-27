package kr.ac.skuniv.service.rent;

import kr.ac.skuniv.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.repository.MemberRepository;
import kr.ac.skuniv.repository.RentRepository;
import kr.ac.skuniv.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RentGetService {

    private final RentRepository rentRepository;
    private final CommonService commonService;

    public RentGetService(RentRepository rentRepository, CommonService commonService) {
        this.rentRepository = rentRepository;
        this.commonService = commonService;
    }

    public Page<RentGetDto> getArtRentHistory(HttpServletRequest request, Long artNo, int pageNum) {
        String userId = commonService.getUserIdByToken(request);
        String userRole = commonService.getUserRoleByToken(request);

        if(!userRole.equals(MemberRole.ARTIST)){
            throw new UserDefineException("작품의 대여목록을 열람할 권한이 없습니다.");
        }
        Page<RentGetDto> rentPage = rentRepository.findRentByArt(userId, artNo, pageNum);

        if(rentPage == null) {
            throw new UserDefineException("대여 기록이 없습니다.");
        }

        return rentPage;
    }

    public Page<RentGetDto> getMemberRentHistory(HttpServletRequest request, int pageNum) {
        String userId = commonService.getUserIdByToken(request);
        String userRole = commonService.getUserRoleByToken(request);

        if(!userRole.equals(MemberRole.CLIENT)){ // FIXME : 고객만 대여할 수 있다고 한다면 냅두고 작가도 대여할 수 있다면 삭제
            throw new UserDefineException("작가는 대여할 수 없습니다.");
        }

        Page<RentGetDto> rentPage = rentRepository.findRentByMember(userId, pageNum);

        if(rentPage == null) {
            throw new UserDefineException("대여한 작품이 없습니다!");
        }

        return rentPage;
    }

}
