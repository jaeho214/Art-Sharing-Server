package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPasswordDto {
    private String userId;
    private String phone;

    @Builder
    public MemberPasswordDto(String userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }
}
