package kr.ac.skuniv.artsharing.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
import kr.ac.skuniv.artsharing.domain.dto.member.SignInDto;
import kr.ac.skuniv.artsharing.domain.dto.member.SignUpDto;
import kr.ac.skuniv.artsharing.service.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/artSharing/sign")
@RequiredArgsConstructor
public class MemberController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final MemberGetService memberGetService;
    private final MemberUpdateService memberUpdateService;
    private final MemberDeleteService memberDeleteService;


    @ApiOperation(value = "회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signUpDto", value="가입할 고객의 정보", required = true, dataType = "SignUpDto")
    })
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody SignUpDto signUpDto) {
        MemberGetDto savedMember = signUpService.signUp(signUpDto);
        return ResponseEntity.created(URI.create("/artSharing/member/" + savedMember.getId())).body(savedMember);
    }

    @ApiOperation(value = "로그인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signInDto", value="로그인할 회원의 정보", required = true, dataType = "SignInDto")
    })
    @PostMapping
    public String signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {
        return signInService.signIn(signInDto,response);
    }

    @ApiOperation(value = "회원 정보 열람")
    @GetMapping
    public ResponseEntity getMemberInfo(@CookieValue(value = "user", required = false) Cookie cookie) {
        return ResponseEntity.ok().body(memberGetService.getMemberInfo(cookie));
    }

    @ApiOperation(value = "작가 리스트 열람")
    @GetMapping("/artistList")
    public ResponseEntity getArtistList(){
        return ResponseEntity.ok().body(memberGetService.getArtistList());
    }


    @ApiOperation(value = "회원 정보 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberUpdateDto", value="수정할 데이터", required = true, dataType = "MemberUpdateDto")
    })
    @PutMapping
    public ResponseEntity updateMember(@CookieValue(value = "user", required = false) Cookie cookie,
                                       @RequestBody MemberUpdateDto memberUpdateDto) {
        return ResponseEntity.ok().body(memberUpdateService.updateMember(cookie, memberUpdateDto));
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity removeMember(@CookieValue(value = "user", required = false) Cookie cookie) {
        return memberDeleteService.deleteMember(cookie);
    }


    @ApiOperation(value = "ID 중복 체크(중복된 ID가 없으면 false)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value="중복 체크할 아이디", required = true, dataType = "string")
    })
    @PostMapping("/check")
    public boolean checkUserId(@RequestBody String userId){
        return signUpService.checkUserID(userId);
    }


    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response){
        return signInService.logout(response);
    }

}
