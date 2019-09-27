package kr.ac.skuniv.artsharing.domain.entity;

import javax.persistence.*;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mno;
	
	//사용자 아이디
	@Column(unique = true)
	private String id;

	private String name;
	private String password;
	private String sex;
	private String age;
	private String affiliation; // 소속
	private String phone;

	@Enumerated(value = EnumType.STRING)
	private MemberRole role; // 역할(작가 or 고객)

	
	@Builder
	public Member(String name, String id, String password, String sex, String age, String affiliation, String phone, MemberRole role) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.affiliation = affiliation;
		this.phone = phone;
		this.role = role;
	}
	
	public void updateMember(MemberUpdateDto memberUpdateDto) {
		this.name = memberUpdateDto.getName();
		this.password = memberUpdateDto.getPassword();
		this.sex = memberUpdateDto.getSex();
		this.age = memberUpdateDto.getAge();
		this.affiliation = memberUpdateDto.getAffiliation();
		this.phone = memberUpdateDto.getPhone();
	}
	
}
