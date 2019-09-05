package kr.ac.skuniv.controller;

import kr.ac.skuniv.domain.dto.ArtRequestDto;
import kr.ac.skuniv.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("artSharing/art")
public class ArtController {

    @Autowired
    ArtService artService;

    //작품 등록
    @PostMapping("/register")
    public void artist_register(@RequestBody ArtRequestDto artRequestDto) {
        artService.registerArt(artRequestDto);
    }

    //작품 수정
    @PutMapping
    public void updateInfo(@RequestBody ArtRequestDto artRequestDto) {
        artService.updateArt(artRequestDto);
    }

    //작품 삭제
    @DeleteMapping
    public void removeMember(Long id){
        artService.deleteArt(id);
    }

    //작품 리스트 가져오기
    @GetMapping
    public List<ArtRequestDto> artList(@RequestHeader(name = "Authorization") String memberId){
        return artService.artList(memberId);
    }
}
