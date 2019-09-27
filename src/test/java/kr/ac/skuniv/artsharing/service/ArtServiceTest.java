package kr.ac.skuniv.artsharing.service;

import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.domain.entity.Reply;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ArtServiceTest {

    private Art art;
    private List<Reply> replyList = new ArrayList<>();
    private Member member;
    private Long artNo = 1L;

    @Before
    public void setUp() throws Exception {

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
