package kr.ac.skuniv.repository.custom;

import kr.ac.skuniv.domain.dto.ArtRequestDto;

import java.util.List;

public interface ArtRepositoryCustom {
    List<ArtRequestDto> searchArt(String keyword);
}
