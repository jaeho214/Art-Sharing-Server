package kr.ac.skuniv.artsharing.repository.rent;

import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import org.springframework.data.domain.Page;

public interface RentRepositoryCustom {
    RentGetDto findRentByArt_IdAndUserId(Long art_id, String userId);
    Page<RentGetDto> findRentByArt(Long artNo, int pageNum);
    Page<RentGetDto> findRentByMember(String userId, int pageNum);
}
