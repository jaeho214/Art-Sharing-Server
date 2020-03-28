package kr.ac.skuniv.artsharing.repository;

import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.repository.custom.ArtRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtRepository extends JpaRepository<Art, Long>, ArtRepositoryCustom {
}
