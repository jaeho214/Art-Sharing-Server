package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateDto {
    private String name;
    private String password;
    private String sex;
    private String age;
    private String affiliation;
    private String phone;

    @Builder
    public MemberUpdateDto(String name, String password, String sex, String age, String affiliation, String phone) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.affiliation = affiliation;
        this.phone = phone;
    }
}
