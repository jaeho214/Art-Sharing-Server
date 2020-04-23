package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPasswordDto {
    @NotNull(message = "아이디를 입력해주세요!")
    private String userId;
    @NotNull(message = "전화번호를 입력해주세요!")
    private String phone;

    @Builder
    public MemberPasswordDto(String userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }
}
