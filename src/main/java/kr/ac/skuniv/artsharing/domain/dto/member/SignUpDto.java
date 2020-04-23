package kr.ac.skuniv.artsharing.domain.dto.member;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpDto {

	@NotNull(message = "이름을 입력해주세요!")
	private String name;
	@NotNull(message = "사용자 아이디를 입력해주세요!")
	private String userId;
	@NotNull(message = "비밀번호를 입력해주세요!")
	private String password;
	@NotNull(message = "성별을 선택해주세요")
	private String sex;
	@NotNull(message = "나이를 입력하세요")
	private String age;
	private String affiliation;
	@NotNull(message = "전화번호를 입력하세요")
	private String phone;
	@NotNull(message = "역할을 지정해주세요")
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
