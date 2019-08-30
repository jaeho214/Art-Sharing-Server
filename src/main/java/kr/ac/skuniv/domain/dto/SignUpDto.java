package kr.ac.skuniv.domain.dto;
/*
    로그인 전용 dto 만들었어
    아이디랑 비밀번호만 받으려고
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String id;
    private String pw;
}
