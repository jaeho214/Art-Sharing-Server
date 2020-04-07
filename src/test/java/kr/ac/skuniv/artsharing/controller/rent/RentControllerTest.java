package kr.ac.skuniv.artsharing.controller.rent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetPagingDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentSaveDto;
import kr.ac.skuniv.artsharing.service.rent.RentGetService;
import kr.ac.skuniv.artsharing.service.rent.RentSaveService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RentControllerTest {

    @InjectMocks
    private RentController rentController;
    @Mock
    private RentGetService rentGetService;
    @Mock
    private RentSaveService rentSaveService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(rentController)
                .alwaysDo(print()).build();
    }

    private RentGetPagingDto rentGetPagingDtoFixture = new EasyRandom().nextObject(RentGetPagingDto.class);
    private RentGetDto rentGetDtoFixture = new EasyRandom().nextObject(RentGetDto.class);
    private RentSaveDto rentSaveDtoFixture = new EasyRandom().nextObject(RentSaveDto.class);
    private Cookie cookie = new Cookie("user", "token");

    @Test
    void saveRent() throws Exception {
        given(rentSaveService.saveRent(any(),any(RentSaveDto.class), anyLong())).willReturn(rentGetDtoFixture);

        mockMvc.perform(
                post("/artSharing/rent/{art_id}", 1L)
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(rentSaveDtoFixture)
                )
        )
                .andExpect(status().isCreated());
    }

    @Test
    void getRentByArt() throws Exception {
        given(rentGetService.getRentByArt(any(), anyLong(), anyInt())).willReturn(rentGetPagingDtoFixture);

        mockMvc.perform(
                get("/artSharing/rent/{art_id}", 1L)
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", String.valueOf(1))
        )
                .andExpect(status().isOk());
    }

    @Test
    void getRent() throws Exception {
        given(rentGetService.getRent(any(), anyInt())).willReturn(rentGetPagingDtoFixture);

        mockMvc.perform(
                get("/artSharing/rent")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", String.valueOf(1))
        )
                .andExpect(status().isOk());
    }

    @Test
    void returnArt() throws Exception {
        given(rentSaveService.returnArt(any(), anyLong())).willReturn(rentGetDtoFixture);

        mockMvc.perform(
                post("/artSharing/rent/return/{rent_id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
        )
                .andExpect(status().isOk());
    }
}