package kr.ac.skuniv.service;

import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.entity.Reply;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ArtServiceTest {

    private Art art;
    private ArtDto artDto;
    private List<Reply> replyList = new ArrayList<>();
    private Member member;
    private Long artNo = 1L;

    @Before
    public void setUp() throws Exception {
        for(int i=0; i < 10; i++){
            replyList.add(
                    Reply.builder()
                    .title("title"+i)
                    .content("content"+i)
                    .regDate(LocalDateTime.of(19, 9, 20, 18, 40))
                    .updateDate(LocalDateTime.of(19, 9, 23, 18, 40))
                    .art(art)
                    .member(member)
                    .build()
            );
        }


        art = Art.builder()
                .artName("artName")
                .price("price")
                .explanation("explanation")
                .regDate(LocalDateTime.of(19, 9, 23, 18, 40))
                .replies(replyList)
                .isRent(false)
                .member(member)
                .build();
    }

    @Test
    public void saveArt() {
    }

    @Test
    public void updateArt() {
    }

    @Test
    public void deleteArt() {
    }

    @Test
    public void getAllArts() {
    }

    @Test
    public void getArtsByUserId() {
    }

    @Test
    public void searchArt() {
    }

    @Test
    public void getArtDetail() {
    }

    @Test
    public void saveReply() {
    }

    @Test
    public void updateReply() {
    }

    @Test
    public void deleteReply() {
    }
}
