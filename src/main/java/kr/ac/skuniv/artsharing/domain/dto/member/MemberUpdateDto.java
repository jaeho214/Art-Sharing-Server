package kr.ac.skuniv.artsharing.domain.dto.member;

import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
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
