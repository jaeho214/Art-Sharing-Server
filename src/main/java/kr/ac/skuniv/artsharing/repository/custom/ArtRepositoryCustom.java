package kr.ac.skuniv.artsharing.repository.custom;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import org.springframework.data.domain.Page;

public interface ArtRepositoryCustom {
    Page<ArtGetDto> searchArt(String searchKeyword, int pageNum);
    Page<ArtGetDto> getArtsByUserId(int pageNum, String userId);
    Page<ArtGetDto> getAllArts(int pageNum);
    ArtGetDetailDto getArtDetail(Long artNo);

}
