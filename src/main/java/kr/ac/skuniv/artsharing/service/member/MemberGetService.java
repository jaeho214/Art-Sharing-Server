package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.repository.member.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGetService {
    private final CommonService commonService;
    private final MemberRepository memberRepository;

    /**
     * 회원 정보 조회
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @return : 조회한 회원 정보
     */
    @Transactional(readOnly = true)
    public MemberGetDto getMember(Cookie cookie) {
        Member member = commonService.getMemberByCookie(cookie);

        return MemberGetDto.of(member);
    }

    /**
     * 모든 작가 조회
     * @return : 작가 리스트
     */
    @Transactional(readOnly = true)
    public List<MemberGetDto> getArtistList() {
        List<Member> memberList = memberRepository.findByRole(MemberRole.ARTIST);
        List<MemberGetDto> artistList = new ArrayList<>();

        for(Member member : memberList){
            artistList.add(
                    MemberGetDto.of(member)
            );
        }

        return artistList;
    }
}
