package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberUpdateService {
    private final CommonService commonService;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원정보 수정
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @param memberUpdateDto : 수정할 데이터
     */
    public MemberGetDto updateMember(Cookie cookie, MemberUpdateDto memberUpdateDto) {
        Member member = commonService.getMemberByCookie(cookie);

        memberUpdateDto.setPassword(passwordEncoder.encode(memberUpdateDto.getPassword()));

        member.updateMember(memberUpdateDto);

        //memberRepository.save(member);
        return MemberGetDto.of(member);
    }
}
