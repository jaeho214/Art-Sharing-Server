package kr.ac.skuniv.artsharing.domain.dto.art;

import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtGetPagingDto {
    private List<ArtGetDto> artPages = new ArrayList<>();
    private boolean isLast;
    private boolean isEmpty;
    private boolean isFirst;

    @Builder
    public ArtGetPagingDto(List<ArtGetDto> artPages, boolean isLast, boolean isEmpty, boolean isFirst) {
        this.artPages = artPages;
        this.isLast = isLast;
        this.isEmpty = isEmpty;
        this.isFirst = isFirst;
    }


    public static ArtGetPagingDto of(Page<ArtGetDto> artPages){
        return ArtGetPagingDto.builder()
                .artPages(artPages.getContent())
                .isEmpty(artPages.isEmpty())
                .isLast(artPages.isLast())
                .isFirst(artPages.isFirst())
                .build();
    }

}
