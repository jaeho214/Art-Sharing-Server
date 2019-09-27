package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    private String id;
    private String pw;
}
