package kr.ac.skuniv.artsharing.repository.custom;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ArtRepositoryCustom {
    Page<ArtGetDto> searchArtByKeyword(String searchKeyword, int pageNum);
    Page<ArtGetDto> getArtsByUserId(String userId, int pageNum);
    Page<ArtGetDto> getAllArts(int pageNum);
    Optional<ArtGetDetailDto> getArtDetail(Long artNo);

}
