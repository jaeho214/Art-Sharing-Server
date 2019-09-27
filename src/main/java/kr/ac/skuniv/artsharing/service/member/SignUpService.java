package kr.ac.skuniv.artsharing.service.member;

import kr.ac.skuniv.artsharing.domain.dto.member.SignUpDto;
import kr.ac.skuniv.artsharing.domain.entity.Member;
import kr.ac.skuniv.artsharing.domain.roles.MemberRole;
import kr.ac.skuniv.artsharing.exception.UserDefineException;
import kr.ac.skuniv.artsharing.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     * @param signUpDto : 회원가입을 진행할 데이터
     * @param roles : 고객과 작가를 구분하기 위한 권한
     */
    public Member signUp(SignUpDto signUpDto, MemberRole roles) {
        Member member = signUpDto.toEntity();
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        if(roles.equals(MemberRole.CLIENT)){
            member.setRole("CLIENT");
        }
        else if(roles.equals(MemberRole.ARTIST)){
            member.setRole("ARTIST");
        }

        return memberRepository.save(member);
    }

    /**
     * ID 중복 체크
     * @param userId : 중복 체크를 하고자 하는 userId
     * @return : 중복된 ID가 없으면 True
     */
    public Boolean checkUserID(String userId) {
        Member member = memberRepository.findById(userId);

        //아아디 존재하면 에러 출력
        if (member != null) {
            throw new UserDefineException("이미 존재하는 아이디입니다!!");
        } else {
            logger.info("아이디 중복 체크 완료");
        }
        return true;
    }
}
