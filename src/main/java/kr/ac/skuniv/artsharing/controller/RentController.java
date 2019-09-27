package kr.ac.skuniv.artsharing.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.Rent;
import kr.ac.skuniv.artsharing.service.rent.RentGetService;
import kr.ac.skuniv.artsharing.service.rent.RentSaveService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/artSharing/rent")
public class RentController {

    private RentGetService rentGetService;
    private RentSaveService rentSaveService;

    public RentController(RentGetService rentGetService, RentSaveService rentSaveService) {
        this.rentGetService = rentGetService;
        this.rentSaveService = rentSaveService;
    }

    @ApiOperation(value = "대여 신청")
    @PostMapping("/{artNo}")
    public ResponseEntity<Rent> saveRent(@CookieValue(value = "user", required = false) Cookie cookie,
                                         @RequestBody RentSaveDto rentSaveDto,
                                         @PathVariable Long artNo){
        return ResponseEntity.ok(rentSaveService.saveRent(cookie, rentSaveDto, artNo));
    }

    @ApiOperation(value = "현재 로그인한 작가의 작품을 대여한 대여목록 조회")
    @GetMapping("/{artNo}/{pageNum}")
    public Page<RentGetDto> getArtRentHistory(@CookieValue(value = "user", required = false) Cookie cookie,
                                              @PathVariable Long artNo,
                                              @PathVariable int pageNum) {
        return rentGetService.getArtRentHistory(cookie, artNo, pageNum);
    }

    @ApiOperation(value = "현재 로그인한 고객이 대여한 대여목록 조회")
    @GetMapping("/{pageNum}")
    public Page<RentGetDto> getMemberRentHistory(@CookieValue(value = "user", required = false) Cookie cookie,
                                                 @PathVariable int pageNum) {
        return rentGetService.getMemberRentHistory(cookie, pageNum);
    }

    @ApiOperation(value = "반납 신청")
    @PostMapping("/return/{artNo}")
    public ResponseEntity<Rent> returnArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                          @PathVariable Long artNo){
        return ResponseEntity.ok(rentSaveService.returnArt(cookie,artNo));
    }


}
