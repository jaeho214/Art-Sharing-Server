package kr.ac.skuniv.artsharing.domain.dto.member;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGetDto {
    private Long id;
    private String name;
    private String userId;
    private String sex;
    private String age;
    private String affiliation;
    private String phone;
    private MemberRole role;

    @Builder
    public MemberGetDto(Long id, String name, String userId, String sex, String age, String affiliation, String phone, MemberRole role) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.sex = sex;
        this.age = age;
        this.affiliation = affiliation;
        this.phone = phone;
        this.role = role;
    }

    public static MemberGetDto of(Member member){
        return MemberGetDto.builder()
                .id(member.getId())
                .name(member.getName())
                .userId(member.getUserId())
                .sex(member.getSex())
                .age(member.getAge())
                .affiliation(member.getAffiliation())
                .phone(member.getPhone())
                .role(member.getRole())
                .build();
    }
}
