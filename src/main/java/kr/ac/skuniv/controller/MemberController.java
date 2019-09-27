package kr.ac.skuniv.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.domain.dto.member.MemberDto;
import kr.ac.skuniv.domain.dto.member.SignUpDto;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/artSharing/sign")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "고객 회원가입")
    @PostMapping("/client")
    public void client_signUp(@RequestBody MemberDto memberDto) {
        memberService.signUp(memberDto, MemberRole.CLIENT);
    }

    @ApiOperation(value = "예술가 회원가입")
    @PostMapping("/artist")
    public void artist_signUp(@RequestBody MemberDto memberDto) {
        memberService.signUp(memberDto, MemberRole.ARTIST);
    }

    @ApiOperation(value = "로그인")
    @PostMapping
    public void login(@RequestBody SignUpDto signUpDto, HttpServletResponse response) {
        memberService.signIn(signUpDto, response);
    }

    @ApiOperation(value = "회원 정보 열람")
    @GetMapping
    public MemberDto getMemberInfo(HttpServletRequest request) {
        return memberService.getMemberInfo(request);
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping
    public void updateMember(HttpServletRequest request, @RequestBody MemberDto memberDto) {
        memberService.updateMember(request, memberDto);
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping
    public void removeMember(HttpServletRequest request) {
        memberService.deleteMember(request);
    }


    @ApiOperation(value = "ID 중복 체크")
    @PostMapping("/check")
    public boolean checkUserId(@RequestBody String userId){
        return memberService.checkUserID(userId);
    }


    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request){
        memberService.logout(request);
        return ResponseEntity.ok().build();
    }

}
