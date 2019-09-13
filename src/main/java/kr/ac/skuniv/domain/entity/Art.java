package kr.ac.skuniv.domain.entity;

import kr.ac.skuniv.domain.dto.ArtRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Art {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   // 작품 등록 번호
	
	private Date date; //대여 날짜
	private String artName; //작품 이름
	private String price; //가격
	private String explanation; //설명
	private boolean rentCheck; //대여 여부 체크

	@CreationTimestamp
    private Timestamp regDate; //등록일

	//단방향으로 작가당 작품 여러개
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "art", fetch = FetchType.LAZY)
    private List<Reply> replies;

//	@OneToMany
//    private List<Rent> rents;

	@Builder
	public Art(Date date, String artName, String price, String explanation, Member member) {
		this.date = date;
		this.artName = artName;
		this.price = price;
		this.explanation = explanation;
		this.member = member;
	}

	public void updateArt(ArtRequestDto artUpdateDto) {
		this.date = artUpdateDto.getDate();
		this.artName = artUpdateDto.getArtName();
		this.price = artUpdateDto.getPrice();
		this.explanation = artUpdateDto.getExplanation();
	}
}
