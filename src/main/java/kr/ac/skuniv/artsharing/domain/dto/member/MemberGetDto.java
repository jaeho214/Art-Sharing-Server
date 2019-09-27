package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberGetDto {
    private Long mno;
    private String name;
    private String id;
    private String sex;
    private String age;
    private String affiliation;
    private String phone;
    private String role;
}
