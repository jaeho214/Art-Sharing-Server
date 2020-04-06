package kr.ac.skuniv.artsharing.service.reply;

import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.exception.reply.ReplyNotFoundException;
import kr.ac.skuniv.artsharing.repository.reply.ReplyRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyDeleteService {

    private final CommonService commonService;
    private final ReplyRepository replyRepository;

    /**
     * 댓글 삭제
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param replyNo : 삭제할 댓글의 번호s
     */
    public ResponseEntity deleteReply(Cookie cookie, Long replyNo) {
        Member member = commonService.getMemberByCookie(cookie);

        Reply reply = replyRepository.findById(replyNo)
                .orElseThrow(ReplyNotFoundException::new);

        commonService.checkAuthority(member.getUserId(), reply.getMember().getUserId());

        reply.delete();
        //replyRepository.delete(reply);

        return ResponseEntity.noContent().build();
    }
}
