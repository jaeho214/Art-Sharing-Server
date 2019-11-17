package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberUpdateDto {
    private String name;
    private String password;
    private String sex;
    private String age;
    private String affiliation;
    private String phone;
}
