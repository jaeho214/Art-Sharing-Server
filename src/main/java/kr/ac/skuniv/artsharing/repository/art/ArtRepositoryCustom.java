package kr.ac.skuniv.artsharing.repository.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArtRepositoryCustom {
    Page<ArtGetDto> searchArtByKeyword(String searchKeyword, int pageNo);
    Page<ArtGetDto> getArtsByUserId(String userId, int pageNo);
    Page<ArtGetDto> getAllArts(int pageNo);
    Page<ArtGetDto> getByRent(int pageNo);
    Optional<ArtGetDetailDto> getArtDetail(Long art_id);

}
