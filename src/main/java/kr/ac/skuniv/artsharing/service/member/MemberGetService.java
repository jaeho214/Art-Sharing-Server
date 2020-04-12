package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.dto.member.MemberPasswordDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.member.MemberNotFoundException;
import kr.ac.skuniv.artsharing.repository.member.MemberRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberGetService {
    private final CommonService commonService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원 정보 조회
     * @param cookie : userId를 조회하기 위한 Cookie 객체
     * @return : 조회한 회원 정보
     */
    @Transactional(readOnly = true)
    public MemberGetDto getMember(Cookie cookie) {
        return MemberGetDto.of(commonService.getMemberByCookie(cookie));
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


    public String findPassword(MemberPasswordDto memberPasswordDto) {
        Member member = memberRepository.findByUserId(memberPasswordDto.getUserId())
                .orElseThrow(MemberNotFoundException::new);

        if(!member.getPhone().equals(memberPasswordDto.getPhone())){
            throw new MemberNotFoundException();
        }

        String newPassword = commonService.generateRandomPassword(15);
        member.updatePassword(passwordEncoder.encode(newPassword));

        return newPassword;
    }



}
