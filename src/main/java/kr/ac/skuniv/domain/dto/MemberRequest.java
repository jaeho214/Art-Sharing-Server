package kr.ac.skuniv.domain.dto;

import kr.ac.skuniv.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequest {

	//이름 / 아이디 / 비밀번호 / 성별 / 나이 / 소속 / 번호 / 개인,작가,기업 구분하는 타입
	private String name;
	private String id;
	private String password;
	private String sex;
	private String age;
	private String affiliation;
	private String phone;
	private String role;

	@Builder
	public MemberRequest(String name, String id, String password, String sex, String age, String affiliation, String phone, String role) {
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
