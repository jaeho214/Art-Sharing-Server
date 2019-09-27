package kr.ac.skuniv.repository.custom;

import kr.ac.skuniv.domain.dto.rent.RentGetDto;
import org.springframework.data.domain.Page;

public interface RentRepositoryCustom {
    RentGetDto findRecentRent(Long artNo);
    Page<RentGetDto> findRentByArt(String userId, Long artNo, int pageNum);
    Page<RentGetDto> findRentByMember(String userId, int pageNum);
}
