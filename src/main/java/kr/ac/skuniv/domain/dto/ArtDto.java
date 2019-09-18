package kr.ac.skuniv.domain.dto;

import kr.ac.skuniv.domain.entity.Art;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ArtDto {

    private Long id; //작품 등록 번호


    //private LocalDate date; //대여 날짜
    private String artName; //작품 이름
    private String price; //가격
    private String explanation; //설명

    @Builder
    public ArtDto(Long id, LocalDateTime date, String artName, String price, String explanation) {
        this.id = id;
        //this.date = date;
        this.artName = artName;
        this.price = price;
        this.explanation = explanation;
    }

    public void addArt(Art art) {

        this.id = art.getId();
        //this.date = art.getRegDate();
        this.artName = art.getArtName();
        this.price = art.getPrice();
        this.explanation = art.getExplanation();

    }

    public Art toEntity() {
        return Art.builder()
                //.regdate(date)
                .artName(artName)
                .price(price)
                .explanation(explanation)
                .build();
    }

    //사용자 아이디
    private String userId;
}
