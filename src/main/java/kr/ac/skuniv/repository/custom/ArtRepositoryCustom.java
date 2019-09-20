package kr.ac.skuniv.repository.custom;

import kr.ac.skuniv.domain.dto.ArtDto;
import org.springframework.data.domain.Page;

public interface ArtRepositoryCustom {
    Page<ArtDto> searchArt(String searchKeyword, int pageNum);
    Page<ArtDto> getArtsByUserId(int pageNum, String userId);
    Page<ArtDto> getAllArts(int pageNum);
    ArtDto getArtDetail(Long artNo);

}
