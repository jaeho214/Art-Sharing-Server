package kr.ac.skuniv.service.art.reply;

import kr.ac.skuniv.domain.entity.Reply;
import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.repository.ReplyRepository;
import kr.ac.skuniv.service.art.ArtCommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ReplyDeleteService {

    private final ArtCommonService artCommon;
    private final ReplyRepository replyRepository;

    public ReplyDeleteService(ArtCommonService artCommon, ReplyRepository replyRepository) {
        this.artCommon = artCommon;
        this.replyRepository = replyRepository;
    }

    /**
     * 댓글 삭제
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @param replyNo : 삭제할 댓글의 번호s
     */
    public void deleteReply(HttpServletRequest request, Long replyNo) {
        String userId = artCommon.getUserIdByToken(request);

        Reply reply = replyRepository.findById(replyNo)
                .orElseThrow(() -> new UserDefineException("해당 댓글을 찾을 수 없습니다."));

        if(!userId.equals(reply.getMember().getId())){
            throw new UserDefineException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        replyRepository.delete(reply);
    }
}
