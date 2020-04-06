package kr.ac.skuniv.artsharing.domain.dto.member;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpDto {

	private String name;
	private String userId;
	private String password;
	private String sex;
	private String age;
	private String affiliation;
	private String phone;
	private String role;


	public Member of(String password, MemberRole role) {
		return Member.builder()
				.name(this.name)
				.userId(this.userId)
				.password(password)
				.sex(this.sex)
				.age(this.age)
				.affiliation(this.affiliation)
				.phone(this.phone)
				.role(role)
				.build();
	}
}
