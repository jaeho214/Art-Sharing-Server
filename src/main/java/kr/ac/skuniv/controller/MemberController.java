package kr.ac.skuniv.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.domain.dto.MemberRequest;
import kr.ac.skuniv.domain.dto.SignUpDto;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/artSharing/sign")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("고객 회원가입")
    @PostMapping("/client")
    public void client_signUp(@RequestBody MemberRequest memberRequest) {
        memberService.signUpMember(memberRequest, MemberRole.CLIENT);
    }

    @ApiOperation("예술가 회원가입")
    @PostMapping("/artist")
    public void artist_signUp(@RequestBody MemberRequest memberRequest) {
        memberService.signUpMember(memberRequest, MemberRole.ARTIST);
    }

    @ApiOperation("로그인")
    @PostMapping
    public String login(@RequestBody SignUpDto signUpDto, HttpServletResponse response) {
        return memberService.loginMember(signUpDto, response);
    }

//    @ApiOperation("회원 정보 열람")
//    @GetMapping
//    public MemberRequest memberInfo(@RequestHeader(name = "Authorization") String token) {
//        return memberService.memberInfo(token);
//    }

    @ApiOperation("회원 정보 열람")
    @GetMapping
    public MemberRequest memberInfo(HttpServletRequest request) {
        return memberService.getMemberInfo(request);
    }

    @ApiOperation("회원 정보 수정")
    @PutMapping
    public void updateInfo(@RequestHeader(name = "Authorization") String token, @RequestBody MemberRequest memberRequest) {
        memberService.updateMember(token, memberRequest);
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public void removeMember(@RequestHeader(name = "Authorization") String token) {
        memberService.deleteMember(token);
    }


    @ApiOperation("ID 중복 체크(false 면 중복된 아이디가 존재)")
    @PostMapping("/check")
    public boolean checkUserId(@RequestBody String userId){
        return memberService.checkUserID(userId);
    }


    /*
    TODO : 토큰 정보를 쿠키로 넘긴다(Filter 수정) / 아이디 중복(o)  / 프론트로 오류 번호 뿌려주기(에러메세지) / 검색 쿼리문에서 like로 걸러주기
    TODO : rql graphql /multipart form 데이터를 받아서 aws에 저장하고 url을 DB에 저장 / 작품 등록일 추가 / 대여 여부
    TODO : 대여 기록 리스트 출력 / 대여 DB 만들어서 join
     */

}
