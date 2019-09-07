package kr.ac.skuniv.controller;

import kr.ac.skuniv.domain.dto.ArtRequestDto;
import kr.ac.skuniv.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("arㄴtSharing/art")
public class ArtController {

    @Autowired
    ArtService artService;

    //작품 등록
    @PostMapping("/register")
    public void artist_register(@RequestHeader(name = "Authorization") String token, @RequestBody ArtRequestDto artRequestDto) {
        artService.registerArt(token, artRequestDto);
    }

    //작품 수정
    @PutMapping
    public void updateInfo(@RequestHeader(name = "Authorization") String token, @RequestBody ArtRequestDto artRequestDto) {
        artService.updateArt(token, artRequestDto);
    }

    //작품 삭제
    @DeleteMapping
    public void removeMember(@RequestHeader(name = "Authorization") String token, Long id){
        artService.deleteArt(token, id);
    }

    //작품 리스트 가져오기
    @GetMapping("/page/{num}")
    public List<ArtRequestDto> artList(@RequestHeader(name = "Authorization") String token, @PathVariable int pageNum){
        return artService.artList(token);
    }
}
