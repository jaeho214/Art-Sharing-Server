package kr.ac.skuniv.artsharing.domain.dto.member;

import kr.ac.skuniv.artsharing.domain.entity.Member;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SignUpDto {

	private String name;
	private String id;
	private String password;
	private String sex;
	private String age;
	private String affiliation;
	private String phone;
	private String role;


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
