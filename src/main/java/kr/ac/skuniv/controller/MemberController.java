package kr.ac.skuniv.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.domain.dto.MemberRequest;
import kr.ac.skuniv.domain.dto.SignUpDto;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artSharing/sign")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("고객 회원가입")
    @PostMapping("/client")
    public void client_signUp(@RequestBody MemberRequest memberRequest){
        memberService.signUpMember(memberRequest, MemberRole.CLIENT);
    }

    @ApiOperation("예술가 회원가입")
    @PostMapping("/artist")
    public void artist_signUp(@RequestBody MemberRequest memberRequest){
        memberService.signUpMember(memberRequest, MemberRole.ARTIST);
    }

    @ApiOperation("로그인")
    @PostMapping
    public String login(@RequestBody SignUpDto signUpDto){
        return memberService.loginMember(signUpDto);
    }

    @ApiOperation("회원 정보 열람")
    @GetMapping
    public MemberRequest memberInfo(@RequestHeader(name = "Authorization") String token){
        return memberService.memberInfo(token);
    }

    @ApiOperation("회원 정보 수정")
    @PutMapping
    public void updateInfo(@RequestHeader(name = "Authorization") String token, @RequestBody MemberRequest memberRequest){
        memberService.updateMember(token, memberRequest);
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public void removeMember(@RequestHeader(name = "Authorization") String token){
        memberService.deleteMember(token);
    }

}
