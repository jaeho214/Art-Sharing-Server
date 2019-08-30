package kr.ac.skuniv.domain.dto;

import kr.ac.skuniv.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {

	//이름 / 아이디 / 비밀번호 / 성별 / 나이 / 소속 / 번호 / 개인,작가,기업 구분하는 타입
	private String name;
	private String identity;
	private String password;
	private String sex;
	private String age;
	private String group;
	private String phone;
	private String type;

	@Builder
	public MemberRequest(String name, String identity, String password, String sex, String age, String group, String phone, String type) {
		this.name = name;
		this.identity = identity;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.group = group;
		this.phone = phone;
		this.type = type;
	}

	public Member toEntity() {
		return Member.builder()
				.name(name)
				.identity(identity)
				.password(password)
				.sex(sex)
				.age(age)
				.group(group)
				.phone(phone)
				.type(type)
				.build();
	}
}
