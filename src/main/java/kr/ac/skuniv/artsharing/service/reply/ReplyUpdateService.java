package kr.ac.skuniv.artsharing.service.reply;

import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.exception.reply.ReplyNotFoundException;
import kr.ac.skuniv.artsharing.repository.reply.ReplyRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyUpdateService {

    private final CommonService commonService;
    private final ReplyRepository replyRepository;


    /**
     * 댓글 수정
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param replyUpdateDto : 댓글을 수정할 데이터
     */
    public ReplyGetDto updateReply(Cookie cookie, ReplyUpdateDto replyUpdateDto) {
        final Member member = commonService.getMemberByCookie(cookie);

        final Reply reply = replyRepository.findById(replyUpdateDto.getReplyNo())
                .orElseThrow(ReplyNotFoundException::new);

        commonService.checkAuthority(member.getUserId(), reply.getMember().getUserId());

        reply.updateReply(replyUpdateDto);

        //replyRepository.save(reply);
        return ReplyGetDto.of(reply);
    }
}
