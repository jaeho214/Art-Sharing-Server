package kr.ac.skuniv.artsharing.repository;

import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.ArtImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtImageRepository extends JpaRepository<ArtImage, Long> {
    Optional<ArtImage> findByArt(Art art);
}
