package kr.ac.skuniv.artsharing.domain.dto.art;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtGetPagingDto {
    private List<ArtGetDto> artPages = new ArrayList<>();
    private boolean isLast;
    private boolean hasContent;
    private boolean isFirst;

    @Builder
    public ArtGetPagingDto(List<ArtGetDto> artPages, boolean isLast, boolean hasContent, boolean isFirst) {
        this.artPages = artPages;
        this.isLast = isLast;
        this.hasContent = hasContent;
        this.isFirst = isFirst;
    }


    public static ArtGetPagingDto of(Page<ArtGetDto> artPages){
        return ArtGetPagingDto.builder()
                .artPages(artPages.getContent())
                .hasContent(artPages.hasContent())
                .isLast(artPages.isLast())
                .isFirst(artPages.isFirst())
                .build();
    }

}
