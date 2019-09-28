package kr.ac.skuniv.artsharing.service.art.reply;

import kr.ac.skuniv.artsharing.domain.dto.reply.ReplySaveDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.domain.entity.Reply;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.ArtRepository;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.repository.ReplyRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class ReplySaveService {

    private final CommonService artCommon;
    private final MemberRepository memberRepository;
    private final ArtRepository artRepository;
    private final ReplyRepository replyRepository;

    public ReplySaveService(CommonService artCommon, MemberRepository memberRepository, ArtRepository artRepository, ReplyRepository replyRepository) {
        this.artCommon = artCommon;
        this.memberRepository = memberRepository;
        this.artRepository = artRepository;
        this.replyRepository = replyRepository;
    }

    /**
     * 댓글 저장
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param replySaveDto : 저장할 댓글의 데이터
     */
    public void saveReply(Cookie cookie, ReplySaveDto replySaveDto) {
        String userId = artCommon.getUserIdByCookie(cookie);

        Member member = memberRepository.findById(userId);
        Art art = artRepository.findById(replySaveDto.getArtNo()).get();

        if(member == null || art == null){
            throw new UserDefineException("댓글을 저장할 수 없습니다.");
        }

        Reply reply = replySaveDto.toEntity(member, art);

        replyRepository.save(reply);
    }
}
