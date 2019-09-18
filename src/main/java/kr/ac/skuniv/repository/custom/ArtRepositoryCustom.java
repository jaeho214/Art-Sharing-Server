package kr.ac.skuniv.repository.custom;

import kr.ac.skuniv.domain.dto.ArtDto;

import java.util.List;

public interface ArtRepositoryCustom {
    List<ArtDto> searchArt(String searchKeyword);
}
