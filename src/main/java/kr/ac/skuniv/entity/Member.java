package kr.ac.skuniv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kr.ac.skuniv.dto.MemberRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//사용자 아이디
	@Column(unique = true)
	private String identity;
	
	//이름  비밀번호 성별 나이 소속 번호 종류
	private String name;
	private String password;
	private String sex;
	private String age;
	private String group;
	private String phone;
	private String type;
	
	public Member() {
		
	}
	
	@Builder
	public Member(String name, String identity, String password, String sex, String age, String group, String phone, String type) {
		this.name = name;
		this.identity = identity;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.group = group;
		this.phone = phone;
		this.type = type;
	}
	
	public void updateMember(MemberRequest request) {
		this.name = request.getName();
		this.password = request.getPassword();
		this.sex = request.getSex();
		this.age = request.getAge();
		this.group = request.getGroup();
		this.phone = request.getPhone();
		this.type = request.getType();
	}
	
}
