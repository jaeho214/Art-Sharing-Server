package kr.ac.skuniv.repository.custom;

import kr.ac.skuniv.domain.dto.ArtDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArtRepositoryCustom {
    List<ArtDto> searchArt(String searchKeyword);
    Page<ArtDto> getArts(int pageNum);
}
