package kr.ac.skuniv.artsharing.controller.reply;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.ac.skuniv.artsharing.controller.reply.ReplyController;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplySaveDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyUpdateDto;
import kr.ac.skuniv.artsharing.service.reply.ReplyDeleteService;
import kr.ac.skuniv.artsharing.service.reply.ReplySaveService;
import kr.ac.skuniv.artsharing.service.reply.ReplyUpdateService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReplyControllerTest {

    @InjectMocks
    private ReplyController replyController;
    @Mock
    private ReplySaveService replySaveService;
    @Mock
    private ReplyUpdateService replyUpdateService;
    @Mock
    private ReplyDeleteService replyDeleteService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(replyController)
                .alwaysDo(print()).build();
    }


    private ReplyGetDto replyGetDtoFixture = new EasyRandom().nextObject(ReplyGetDto.class);
    private ReplySaveDto replySaveDtoFixture = new EasyRandom().nextObject(ReplySaveDto.class);
    private ReplyUpdateDto replyUpdateDtoFixture = new EasyRandom().nextObject(ReplyUpdateDto.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void saveReply() throws Exception {
        given(replySaveService.saveReply(any(), any(ReplySaveDto.class))).willReturn(replyGetDtoFixture);

        mockMvc.perform(
                post("/artSharing/art/reply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(replySaveDtoFixture)
                )
                .cookie(cookie)
        )
                .andExpect(status().isCreated());
    }

    @Test
    void updateReply() throws Exception{
        given(replyUpdateService.updateReply(any(), any(ReplyUpdateDto.class))).willReturn(replyGetDtoFixture);

        mockMvc.perform(
                put("/artSharing/art/reply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(replyUpdateDtoFixture)
                )
                .cookie(cookie)
        )
                .andExpect(status().isOk());
    }


    @Test
    void deleteReply() throws Exception {
        given(replyDeleteService.deleteReply(any(), anyLong())).willReturn(ResponseEntity.noContent().build());

        mockMvc.perform(
                delete("/artSharing/art/reply/{reply_id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
        )
                .andExpect(status().isNoContent());
    }
}