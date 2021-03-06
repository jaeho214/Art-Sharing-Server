package kr.ac.skuniv.artsharing.domain.dto.rent;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentGetPagingDto {
    private List<RentGetDto> rentGetDtoList = new ArrayList<>();
    private boolean isLast;
    private boolean hasContent;
    private boolean isFirst;

    @Builder
    public RentGetPagingDto(List<RentGetDto> rentGetDtoList,
                             boolean isLast,
                             boolean hasContent,
                             boolean isFirst) {
        this.rentGetDtoList = rentGetDtoList;
        this.isLast = isLast;
        this.hasContent = hasContent;
        this.isFirst = isFirst;
    }

    public static RentGetPagingDto of(Page<RentGetDto> rentPages){
        return RentGetPagingDto.builder()
                .rentGetDtoList(rentPages.getContent())
                .hasContent(rentPages.hasContent())
                .isFirst(rentPages.isFirst())
                .isLast(rentPages.isLast())
                .build();
    }
}
