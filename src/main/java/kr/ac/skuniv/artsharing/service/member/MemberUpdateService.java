package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class MemberUpdateService {
    private final CommonService commonService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberUpdateService(CommonService commonService, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.commonService = commonService;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원정보 수정
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param memberUpdateDto : 수정할 데이터
     */
    public void updateMember(Cookie cookie, MemberUpdateDto memberUpdateDto) {
        String userId = commonService.getUserIdByCookie(cookie);

        Member member = memberRepository.findById(userId);

        if(member == null)
            throw new UserDefineException("회원 정보를 수정할 수 없습니다.");

        memberUpdateDto.setPassword(passwordEncoder.encode(memberUpdateDto.getPassword()));

        member.updateMember(memberUpdateDto);

        memberRepository.save(member);

    }
}
