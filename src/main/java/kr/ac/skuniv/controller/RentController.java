package kr.ac.skuniv.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.domain.dto.rent.RentSaveDto;
import kr.ac.skuniv.domain.entity.Rent;
import kr.ac.skuniv.service.rent.RentGetService;
import kr.ac.skuniv.service.rent.RentSaveService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<Rent> saveRent(HttpServletRequest request, @RequestBody RentSaveDto rentSaveDto, @PathVariable Long artNo){
        return ResponseEntity.ok(rentSaveService.saveRent(request, rentSaveDto, artNo));
    }

    @ApiOperation(value = "현재 로그인한 작가의 작품을 대여한 대여목록 조회")
    @GetMapping("/{artNo}/{pageNum}")
    public Page<RentGetDto> getArtRentHistory(HttpServletRequest request, @PathVariable Long artNo,@PathVariable int pageNum) {
        return rentGetService.getArtRentHistory(request, artNo, pageNum);
    }

    @ApiOperation(value = "현재 로그인한 고객이 대여한 대여목록 조회")
    @GetMapping("/{pageNum}")
    public Page<RentGetDto> getMemberRentHistory(HttpServletRequest request, @PathVariable int pageNum) {
        return rentGetService.getMemberRentHistory(request, pageNum);
    }

    @ApiOperation(value = "반납 신청")
    @GetMapping("/return/{artNo}")
    public ResponseEntity<Rent> returnArt(HttpServletRequest request, @PathVariable Long artNo){
        return ResponseEntity.ok(rentSaveService.returnArt(request,artNo));
    }


}
