package kr.ac.skuniv.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Art {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   // 작품 등록 번호
	
	private Date date; //대여 날짜
	private String artName; //작품 이름
	private String price; //가격
	private String explanation; //설명

	//단방향으로 작가당 작품 여러개
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
