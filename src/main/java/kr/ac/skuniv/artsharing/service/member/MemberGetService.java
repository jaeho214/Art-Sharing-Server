package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MemberGetService {
    private final CommonService commonService;
    private final MemberRepository memberRepository;

    public MemberGetService(CommonService commonService, MemberRepository memberRepository) {
        this.commonService = commonService;
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 정보 조회
     * @param request : userId를 조회하기 위한 HttpServletRequest 객체
     * @return : 조회한 회원 정보
     */
    public MemberGetDto getMemberInfo(HttpServletRequest request) {
        String userId = commonService.getUserIdByToken(request);

        Member member = memberRepository.findById(userId);

        if(member == null)
            throw new UserDefineException("존재하지 않는 회원입니다");

        return MemberGetDto.builder()
                .mno(member.getMno())
                .id(member.getId())
                .name(member.getName())
                .affiliation(member.getAffiliation())
                .phone(member.getPhone())
                .sex(member.getSex())
                .age(member.getAge())
                .role(member.getRole())
                .build();
    }
}
