package kr.ac.skuniv.artsharing.controller.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.ac.skuniv.artsharing.controller.member.MemberController;
import kr.ac.skuniv.artsharing.domain.dto.member.MemberGetDto;
import kr.ac.skuniv.artsharing.domain.dto.member.MemberUpdateDto;
import kr.ac.skuniv.artsharing.domain.dto.member.SignInDto;
import kr.ac.skuniv.artsharing.domain.dto.member.SignUpDto;
import kr.ac.skuniv.artsharing.service.member.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;
    @Mock
    private SignUpService signUpService;
    @Mock
    private SignInService signInService;
    @Mock
    private MemberGetService memberGetService;
    @Mock
    private MemberUpdateService memberUpdateService;
    @Mock
    private MemberDeleteService memberDeleteService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .alwaysDo(print()).build();
    }

    private SignUpDto signUpDtoFixture = new EasyRandom().nextObject(SignUpDto.class);
    private SignInDto signInDtoFixture = new EasyRandom().nextObject(SignInDto.class);
    private MemberGetDto memberGetDtoFixture = new EasyRandom().nextObject(MemberGetDto.class);
    private MemberUpdateDto memberUpdateDtoFixture = new EasyRandom().nextObject(MemberUpdateDto.class);
    private List<MemberGetDto> memberGetDtoList = Arrays.asList(memberGetDtoFixture);
    private String token = "token";
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void signUp() throws Exception {
        given(signUpService.signUp(any(SignUpDto.class))).willReturn(memberGetDtoFixture);

        mockMvc.perform(
                post("/artSharing/sign/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(signUpDtoFixture)
                )
        )
                .andExpect(status().isCreated());
    }
    @Test
    void signIn() throws Exception{
        given(signInService.signIn(any(SignInDto.class), any())).willReturn(token);

        mockMvc.perform(
                post("/artSharing/sign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(signInDtoFixture)
                )
        )
                .andExpect(status().isOk());
    }
    @Test
    void getMember() throws Exception {
        given(memberGetService.getMember(any())).willReturn(memberGetDtoFixture);

        mockMvc.perform(
                get("/artSharing/sign")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    void getArtistList() throws Exception {
        given(memberGetService.getArtistList()).willReturn(memberGetDtoList);

        mockMvc.perform(
                get("/artSharing/sign/artistList")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }
    @Test
    void updateMember() throws Exception {
        given(memberUpdateService.updateMember(any(), any(MemberUpdateDto.class))).willReturn(memberGetDtoFixture);

        mockMvc.perform(
                put("/artSharing/sign")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(
                        objectMapper.writeValueAsString(memberUpdateDtoFixture)
                )
        )
                .andExpect(status().isOk());
    }
    @Test
    void deleteMember() throws Exception {
        given(memberDeleteService.deleteMember(any())).willReturn(ResponseEntity.noContent().build());

        mockMvc.perform(
                delete("/artSharing/sign")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
        )
                .andExpect(status().isNoContent());
    }
    @Test
    void checkUserId() throws Exception {
        given(signUpService.checkUserId(anyString())).willReturn(false);

        mockMvc.perform(
                get("/artSharing/sign/check")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "userId")
        )
                .andExpect(status().isOk());
    }
    @Test
    void logout() throws Exception {
        given(signInService.logout(any())).willReturn(ResponseEntity.noContent().build());

        mockMvc.perform(
                get("/artSharing/sign/logout")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());

    }
}