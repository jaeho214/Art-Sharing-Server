package kr.ac.skuniv.artsharing.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
public class ArtImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artImageNo;

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
    @JoinColumn(name = "Art_no")
    private Art art;

    public void updateArtImage(ArtImage artImage){
        this.imageName = artImage.imageName;
        this.imageType = artImage.imageType;
        this.imagePath = artImage.imagePath;
        this.imageSize = artImage.imageSize;
        this.imageUrl = artImage.imageUrl;
    }

}
