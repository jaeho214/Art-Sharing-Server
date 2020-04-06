package kr.ac.skuniv.artsharing.service.reply;

import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyUpdateDto;
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

import javax.servlet.http.Cookie;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReplyUpdateServiceTest {

    @InjectMocks
    private ReplyUpdateService replyUpdateService;

    @Mock
    private CommonService commonService;
    @Mock
    private ReplyRepository replyRepository;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Reply replyFixture = new EasyRandom().nextObject(Reply.class);
    private ReplyUpdateDto replyUpdateDtoFixture = new EasyRandom().nextObject(ReplyUpdateDto.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void updateReply() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(replyRepository.findById(anyLong())).willReturn(Optional.ofNullable(replyFixture));

        //when
        ReplyGetDto updatedReply = replyUpdateService.updateReply(cookie, replyUpdateDtoFixture);

        //then
        assertThat(updatedReply.getContent()).isEqualTo(replyUpdateDtoFixture.getContent());
    }
}