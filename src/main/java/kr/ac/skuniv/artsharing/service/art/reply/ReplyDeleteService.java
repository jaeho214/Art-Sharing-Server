package kr.ac.skuniv.artsharing.service.art.reply;

import kr.ac.skuniv.artsharing.domain.entity.Reply;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ReplyRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class ReplyDeleteService {

    private final CommonService artCommon;
    private final ReplyRepository replyRepository;

    public ReplyDeleteService(CommonService artCommon, ReplyRepository replyRepository) {
        this.artCommon = artCommon;
        this.replyRepository = replyRepository;
    }

    /**
     * 댓글 삭제
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param replyNo : 삭제할 댓글의 번호s
     */
    public void deleteReply(Cookie cookie, Long replyNo) {
        String userId = artCommon.getUserIdByCookie(cookie);

        Reply reply = replyRepository.findById(replyNo)
                .orElseThrow(() -> new UserDefineException("해당 댓글을 찾을 수 없습니다."));

        if(!userId.equals(reply.getMember().getId())){
            throw new UserDefineException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        replyRepository.delete(reply);
    }
}
