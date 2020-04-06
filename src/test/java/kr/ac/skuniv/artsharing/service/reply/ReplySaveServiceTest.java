package kr.ac.skuniv.artsharing.service.reply;

import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplySaveDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
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
class ReplySaveServiceTest {

    @InjectMocks
    private ReplySaveService replySaveService;

    @Mock
    private CommonService commonService;
    @Mock
    private ArtRepository artRepository;
    @Mock
    private ReplyRepository replyRepository;

    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private Reply replyFixture = new EasyRandom().nextObject(Reply.class);
    private ReplySaveDto replySaveDtoFixture = new EasyRandom().nextObject(ReplySaveDto.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void saveReply() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.findById(anyLong())).willReturn(Optional.ofNullable(artFixture));
        given(replyRepository.save(any(Reply.class))).willReturn(replyFixture);

        //when
        ReplyGetDto savedReply = replySaveService.saveReply(cookie, replySaveDtoFixture);

        //then
        assertThat(savedReply.getContent()).isEqualTo(replySaveDtoFixture.getContent());
    }
}