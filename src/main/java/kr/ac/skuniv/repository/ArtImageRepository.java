package kr.ac.skuniv.repository;

import kr.ac.skuniv.domain.entity.ArtImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtImageRepository extends JpaRepository<ArtImage, Long> {
}
