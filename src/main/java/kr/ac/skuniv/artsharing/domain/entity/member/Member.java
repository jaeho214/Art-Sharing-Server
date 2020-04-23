package kr.ac.skuniv.artsharing.domain.entity.member;

import javax.persistence.*;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.util.JpaBasePersistable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
@Where(clause = "deleted=0")
@DynamicUpdate
public class Member extends JpaBasePersistable {
	
	//사용자 아이디
	@Column(unique = true)
	private String userId;

	private String name;
	private String password;
	private String sex;
	private String age;
	private String affiliation; // 소속
	private String phone;

	@Enumerated(value = EnumType.STRING)
	private MemberRole role; // 역할(작가 or 고객)

	
	@Builder
	public Member(String name, String userId, String password, String sex, String age, String affiliation, String phone, MemberRole role) {
		this.name = name;
		this.userId = userId;
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

	public void updatePassword(String password){
		this.password = password;
	}
	
}
