package kr.ac.skuniv.artsharing.service.reply;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import kr.ac.skuniv.artsharing.repository.reply.ReplyRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReplyDeleteServiceTest {

    @InjectMocks
    private ReplyDeleteService replyDeleteService;
    @Mock
    private CommonService commonService;
    @Mock
    private ReplyRepository replyRepository;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Reply replyFixture = new EasyRandom().nextObject(Reply.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void deleteReply() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(replyRepository.findById(anyLong())).willReturn(Optional.ofNullable(replyFixture));

        //when
        ResponseEntity responseEntity = replyDeleteService.deleteReply(cookie, 1L);

        //then
        assertThat(responseEntity).isEqualTo(ResponseEntity.noContent().build());
    }
}