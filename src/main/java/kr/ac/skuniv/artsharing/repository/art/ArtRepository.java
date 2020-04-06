package kr.ac.skuniv.artsharing.repository.art;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtRepository extends JpaRepository<Art, Long>, ArtRepositoryCustom {
}
