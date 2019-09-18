package kr.ac.skuniv.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.domain.dto.RentDto;
import kr.ac.skuniv.service.RentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/artSharing/rent")
public class RentController {

    private RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/{artNo}")
    @ApiOperation(value = "작품의 대여기록 조회")
    public List<RentDto> getArtRentHistory(@PathVariable Long artNo) {
        return rentService.getArtRentHistory(artNo);
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "회원의 대여기록 조회")
    public List<RentDto> getMemberRentHistory(@PathVariable String userId) {
        return rentService.getMemberRentHistory(userId);
    }

    @PostMapping
    public void postRent(@RequestBody RentDto rentDto){
        rentService.postRent(rentDto);
    }
}
