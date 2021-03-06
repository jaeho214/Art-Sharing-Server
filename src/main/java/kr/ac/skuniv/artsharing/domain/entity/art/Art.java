package kr.ac.skuniv.artsharing.domain.entity.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import kr.ac.skuniv.artsharing.util.JpaBasePersistable;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "art")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "art_id"))
@Where(clause = "deleted=0")
@DynamicUpdate
public class Art extends JpaBasePersistable {

	@Column(name = "art_name", length = 50, nullable = false)
	private String artName; //작품 이름
	@Column(name = "price", length = 30, nullable = false)
	private String price; //하루당 가격
	@Column(name = "explanation", length = 100, nullable = false)
	private String explanation; //설명

	@Column(name = "isRent", nullable = false)
	private boolean isRent = false; //대여 여부 체크

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "art", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "artImage_id")
//	private ArtImage artImage;

	@Builder
	public Art(String artName,
			   String price,
			   String explanation,
			   boolean isRent,
			   Member member,
			   List<Reply> replies,
			   String imageUrl) {
		this.artName = artName;
		this.price = price;
		this.explanation = explanation;
		this.isRent = isRent;
		this.member = member;
		this.replies = replies;
		this.imageUrl = imageUrl;
	}

	public void updateArt(ArtUpdateDto artUpdateDto) {
		this.artName = artUpdateDto.getArtName();
		this.price = artUpdateDto.getPrice();
		this.explanation = artUpdateDto.getExplanation();
		this.imageUrl = artUpdateDto.getImageUrl();
	}

	public void updateArtImage(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public void changeRentStatus(Boolean isRent){
		this.isRent = isRent;
	}
}
