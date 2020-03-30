package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberDeleteService {
    private final CommonService commonService;


    /**
     * 회원 탈퇴
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     */
    public ResponseEntity deleteMember(Cookie cookie) {
        Member member= commonService.getMemberByCookie(cookie);

        //memberRepository.delete(member);
        member.delete();
        return ResponseEntity.noContent().build();
    }
}
