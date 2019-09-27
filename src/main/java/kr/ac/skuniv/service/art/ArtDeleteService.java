package kr.ac.skuniv.service.art;

import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.repository.ArtRepository;
import kr.ac.skuniv.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ArtDeleteService {

    private final CommonService commonService;
    private final ArtRepository artRepository;

    public ArtDeleteService(CommonService commonService, ArtRepository artRepository) {
        this.commonService = commonService;
        this.artRepository = artRepository;
    }


    /**
     * 작품 삭제
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param id : 수정할 작품의 번호
     */
    public Art deleteArt(HttpServletRequest request, Long id) {
        String userId = commonService.getUserIdByToken(request);

        if(userId == null)
            throw new UserDefineException("로그인이 필요합니다.");

        Art art = artRepository.findByMemberIdAndId(userId, id);

        if(!userId.equals(art.getMember().getId())){
            throw new UserDefineException("작품을 수정할 권한이 없습니다.");
        }

        artRepository.delete(art);

        return art;
    }

}
