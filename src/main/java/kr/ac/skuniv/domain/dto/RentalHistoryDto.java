package kr.ac.skuniv.domain.dto;

import kr.ac.skuniv.domain.entity.RentalHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RentalHistoryDto {

    private boolean historyCheck;

    @Builder
    public RentalHistoryDto(boolean historyCheck) {
        this.historyCheck = historyCheck;
    }

    public RentalHistory toEntity() {
        return RentalHistory.builder()
                .historyCheck(historyCheck)
                .build();
    }

    private String userId;
    private String artistId;
}
