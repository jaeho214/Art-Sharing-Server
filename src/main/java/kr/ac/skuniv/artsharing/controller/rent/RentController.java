package kr.ac.skuniv.artsharing.controller.rent;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.rent.Rent;
import kr.ac.skuniv.artsharing.service.rent.RentGetService;
import kr.ac.skuniv.artsharing.service.rent.RentSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.net.URI;

@RestController
@RequestMapping(value = "/artSharing/rent")
@RequiredArgsConstructor
public class RentController {

    private final RentGetService rentGetService;
    private final RentSaveService rentSaveService;


    @ApiOperation(value = "대여 신청")
    @PostMapping("/{art_id}")
    public ResponseEntity saveRent(@CookieValue(value = "user", required = false) Cookie cookie,
                                   @RequestBody RentSaveDto rentSaveDto,
                                   @PathVariable Long art_id){
        RentGetDto savedRent = rentSaveService.saveRent(cookie, rentSaveDto, art_id);
        return ResponseEntity.created(URI.create("/artSharing/rent" + savedRent.getRent_id())).body(savedRent);
    }

    @ApiOperation(value = "작가가 본인 작품의 대여기록 조회")
    @GetMapping("/{art_id}")
    public ResponseEntity getRentByArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                       @PathVariable Long art_id,
                                       @RequestParam int pageNo) {
        return ResponseEntity.ok().body(rentGetService.getRentByArt(cookie, art_id, pageNo));
    }

    @ApiOperation(value = "현재 로그인한 고객이 대여한 대여목록 조회")
    @GetMapping
    public ResponseEntity getRent(@CookieValue(value = "user", required = false) Cookie cookie,
                                                 @RequestParam int pageNo) {
        return ResponseEntity.ok().body(rentGetService.getRent(cookie, pageNo));
    }

    @ApiOperation(value = "반납 신청")
    @PostMapping("/return/{rent_id}")
    public ResponseEntity returnArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                    @PathVariable Long rent_id){
        return ResponseEntity.ok().body(rentSaveService.returnArt(cookie,rent_id));
    }


}
