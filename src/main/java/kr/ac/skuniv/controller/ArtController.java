package kr.ac.skuniv.controller;

import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("artSharing/art")
public class ArtController {

    @Autowired
    ArtService artService;

    //작품 등록
    @PostMapping("/register")
    public void artist_register(HttpServletRequest request, @RequestBody ArtDto artRequestDto) {
        artService.registerArt(request, artRequestDto);
    }

    //작품 수정
    @PutMapping
    public void updateInfo(HttpServletRequest request, @RequestBody ArtDto artRequestDto) {
        artService.updateArt(request, artRequestDto);
    }

    //작품 삭제
    @DeleteMapping
    public void removeMember(HttpServletRequest request, Long id){
        artService.deleteArt(request, id);
    }

    //작품 리스트 가져오기
    @GetMapping("/page/{pageNum}")
    public List<ArtDto> artList(HttpServletRequest request, @PathVariable int pageNum){
        return artService.artList(request);
    }

    @GetMapping("/search/{keyword}")
    public List<ArtDto> searchArt(@PathVariable String keyword){
        return artService.searchArt(keyword);
    }
}
