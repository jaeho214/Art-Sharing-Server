package kr.ac.skuniv.artsharing.repository.artImage;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.artImage.ArtImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtImageRepository extends JpaRepository<ArtImage, Long> {
    Optional<ArtImage> findByArt(Art art);
}
