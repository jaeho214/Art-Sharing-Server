package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MemberDeleteService {
    private final CommonService commonService;
    private final MemberRepository memberRepository;

    public MemberDeleteService(CommonService commonService, MemberRepository memberRepository) {
        this.commonService = commonService;
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 탈퇴
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     */
    public void deleteMember(HttpServletRequest request) {
        String userId = commonService.getUserIdByToken(request);

        Member member = memberRepository.findById(userId);

        if(member == null)
            throw new UserDefineException("회원 정보를 삭제할 수 없습니다.");

        memberRepository.delete(member);

    }
}
