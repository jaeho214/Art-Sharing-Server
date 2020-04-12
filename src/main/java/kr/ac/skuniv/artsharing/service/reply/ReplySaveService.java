package kr.ac.skuniv.artsharing.service.reply;

import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplySaveDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.reply.Reply;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.exception.art.ArtNotFoundException;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.repository.reply.ReplyRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class ReplySaveService {

    private final CommonService commonService;
    private final ArtRepository artRepository;
    private final ReplyRepository replyRepository;

    /**
     * 댓글 저장
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param replySaveDto : 저장할 댓글의 데이터
     */
    public ReplyGetDto saveReply(Cookie cookie, ReplySaveDto replySaveDto) {
        Member member = commonService.getMemberByCookie(cookie);

        Art art = artRepository.findById(replySaveDto.getArtNo())
                .orElseThrow(ArtNotFoundException::new);

        return ReplyGetDto.of(replyRepository.save(replySaveDto.of(member, art)));
    }
}
