package kr.ac.skuniv.dto;

import kr.ac.skuniv.entity.Member;
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
