package kr.ac.skuniv.artsharing.domain.entity;

import kr.ac.skuniv.artsharing.util.JpaBasePersistable;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "artImage_id"))
@Where(clause = "deleted=0")
public class ArtImage extends JpaBasePersistable {
    @NotNull
    private String imageName;
    @NotNull
    private String imageType;
    @NotNull
    private String imagePath;
    @NotNull
    private Long imageSize;
    @NotNull
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "art_id")
    private Art art;

    public void updateArtImage(ArtImage artImage){
        this.imageName = artImage.imageName;
        this.imageType = artImage.imageType;
        this.imagePath = artImage.imagePath;
        this.imageSize = artImage.imageSize;
        this.imageUrl = artImage.imageUrl;
    }

    @Builder
    public ArtImage(String imageName,
                    String imageType,
                    String imagePath,
                    Long imageSize,
                    String imageUrl,
                    Art art) {
        this.imageName = imageName;
        this.imageType = imageType;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
        this.imageUrl = imageUrl;
        this.art = art;
    }

}
