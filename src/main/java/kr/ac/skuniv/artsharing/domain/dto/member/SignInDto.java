package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInDto {
    private String userId;
    private String password;

    public SignInDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
