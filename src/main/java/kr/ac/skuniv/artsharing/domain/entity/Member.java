package kr.ac.skuniv.artsharing.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
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
	private String role; // 역할(작가 or 고객)

	
	@Builder
	public Member(String name, String id, String password, String sex, String age, String affiliation, String phone, String role) {
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
		this.role = memberUpdateDto.getRole();
	}
	
}
