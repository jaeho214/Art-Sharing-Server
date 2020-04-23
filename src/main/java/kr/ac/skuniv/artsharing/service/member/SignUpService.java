package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.dto.member.SignUpDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param signUpDto : 회원가입을 진행할 데이터
     */
    public MemberGetDto signUp(SignUpDto signUpDto) {
        final Member member = signUpDto.of(passwordEncoder.encode(signUpDto.getPassword()),
                                        MemberRole.fromString(signUpDto.getRole()));

        return MemberGetDto.of(memberRepository.save(member));
    }

    /**
     * ID 중복 체크
     * @param userId : 중복 체크를 하고자 하는 userId
     * @return : 중복된 ID가 없으면 false
     */
    public Boolean checkUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }
}
