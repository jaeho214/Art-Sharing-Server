package kr.ac.skuniv.artsharing.domain.dto.member;

import lombok.*;

/**
 *  작
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ArtistGetDto {
    private Long mno;
    private String name;
    private String id;
    private String sex;
    private String age;
    private String affiliation;
}
