package kr.ac.skuniv.artsharing.service.art.reply;

import kr.ac.skuniv.artsharing.domain.dto.ReplyDto;
import kr.ac.skuniv.artsharing.domain.entity.Reply;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ReplyRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class ReplyUpdateService {

    private final CommonService artCommon;
    private final ReplyRepository replyRepository;

    public ReplyUpdateService(CommonService artCommon, ReplyRepository replyRepository) {
        this.artCommon = artCommon;
        this.replyRepository = replyRepository;
    }

    /**
     * 댓글 수정
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param replyDto : 댓글을 수정할 데이터
     */
    public void updateReply(Cookie cookie, ReplyDto replyDto) {
        String userId = artCommon.getUserIdByCookie(cookie);

        Reply reply = replyRepository.findById(replyDto.getReplyNo())
                .orElseThrow(() -> new UserDefineException("해당 댓글을 찾을 수 없습니다."));

        if(!userId.equals(reply.getMember().getId())){
            throw new UserDefineException("해당 댓글을 수정할 권한이 없습니다.");
        }

        reply.updateReply(replyDto);

        replyRepository.save(reply);
    }
}
