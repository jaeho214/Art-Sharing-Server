package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SignInDto {
    @NotNull(message = "아이디를 입력해주세요!")
    private String userId;
    @NotNull(message = "비밀번호를 입력해주세요!")
    private String password;

    public SignInDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
