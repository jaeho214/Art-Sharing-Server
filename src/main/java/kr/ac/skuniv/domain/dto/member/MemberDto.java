package kr.ac.skuniv.domain.dto.member;

import kr.ac.skuniv.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

	private String name;
	private String id;
	private String password;
	private String sex;
	private String age;
	private String affiliation;
	private String phone;
	private String role;

	@Builder
	public MemberDto(String name, String id, String password, String sex, String age, String affiliation, String phone, String role) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.affiliation = affiliation;
		this.phone = phone;
		this.role = role;
	}

	public Member toEntity() {
		return Member.builder()
				.name(name)
				.id(id)
				.password(password)
				.sex(sex)
				.age(age)
				.affiliation(affiliation)
				.phone(phone)
				.role(role)
				.build();
	}
}
