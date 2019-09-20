package kr.ac.skuniv.controller;

import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public void registerArt(HttpServletRequest request, @RequestBody ArtDto artRequestDto) {
        artService.registerArt(request, artRequestDto);
    }

    //작품 수정
    @PutMapping
    public void updateArt(HttpServletRequest request, @RequestBody ArtDto artRequestDto) {
        artService.updateArt(request, artRequestDto);
    }

    //작품 삭제
    @DeleteMapping
    public void deleteArt(HttpServletRequest request, Long id){
        artService.deleteArt(request, id);
    }

    //모든 작품 리스트 가져오기
    @GetMapping("/ArtsList/{pageNum}")
    public Page<ArtDto> getAllArts(@PathVariable int pageNum){
        return artService.getAllArts(pageNum);
    }

    //작가의 작품 리스트 가져오기
    @GetMapping("/{pageNum}")
    public Page<ArtDto> getArtsByUserId(HttpServletRequest request, @PathVariable int pageNum){
        return artService.getArtsByUserId(request,pageNum);
    }

    //작품 상세보기
    @GetMapping("/{artNo}")
    public ArtDto getArtDetail(@PathVariable Long artNo){
        return artService.getArtDetail(artNo);
    }

    @GetMapping("/search/{keyword}/{pageNum}")
    public Page<ArtDto> searchArt(@PathVariable String keyword, @PathVariable int pageNum){
        return artService.searchArt(keyword, pageNum);
    }
}
