package kr.ac.skuniv.domain.entity;

import kr.ac.skuniv.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.domain.dto.art.ArtUpdateDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Art {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   // 작품 등록 번호

	private String artName; //작품 이름
	private String price; //하루당 가격
	private String explanation; //설명

	@Column(nullable = false)
	private boolean isRent = false; //대여 여부 체크

	@CreationTimestamp
    private LocalDate regDate; //등록일

	//단방향으로 작가당 작품 여러개
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "art", fetch = FetchType.LAZY)
    private List<Reply> replies;

//	@OneToMany
//    private List<Rent> rents;

	@Builder
	public Art(String artName, String price, String explanation, boolean isRent, LocalDate regDate, Member member, List<Reply> replies) {
		this.artName = artName;
		this.price = price;
		this.explanation = explanation;
		this.isRent = isRent;
		this.regDate = regDate;
		this.member = member;
		this.replies = replies;
	}

	public void updateArt(ArtUpdateDto artUpdateDto) {
		this.artName = artUpdateDto.getArtName();
		this.price = artUpdateDto.getPrice();
		this.explanation = artUpdateDto.getExplanation();
	}

	public void changeRentStatus(Boolean isRent){
		this.isRent = isRent;
	}
}
