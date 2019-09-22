package kr.ac.skuniv.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.domain.dto.ReplyDto;
import kr.ac.skuniv.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("artSharing/art")
public class ArtController {

    ArtService artService;

    public ArtController(ArtService artService) {
        this.artService = artService;
    }

    @ApiOperation(value = "작품 등록")
    @PostMapping("/register")
    public void saveArt(HttpServletRequest request, @RequestBody ArtDto artRequestDto) {
        artService.saveArt(request, artRequestDto);
    }

    @ApiOperation(value = "작품 정보 수정")
    @PutMapping
    public void updateArt(HttpServletRequest request, @RequestBody ArtDto artRequestDto) {
        artService.updateArt(request, artRequestDto);
    }

    @ApiOperation(value = "작품 삭제")
    @DeleteMapping("/{artNo}")
    public void deleteArt(HttpServletRequest request,@PathVariable Long artNo){
        artService.deleteArt(request, artNo);
    }

    @ApiOperation(value = "모든 작품 조회")
    @GetMapping("/artsList/{pageNum}")
    public Page<ArtDto> getAllArts(@PathVariable int pageNum){
        return artService.getAllArts(pageNum);
    }

    @ApiOperation(value = "작가의 작품 조회")
    @GetMapping("/{pageNum}")
    public Page<ArtDto> getArtsByUserId(HttpServletRequest request, @PathVariable int pageNum){
        return artService.getArtsByUserId(request,pageNum);
    }

    @ApiOperation(value = "작품 상세보기")
    @GetMapping("/detail/{artNo}")
    public ArtDto getArtDetail(@PathVariable Long artNo){
        return artService.getArtDetail(artNo);
    }

    @ApiOperation(value = "작품명 및 작가이름 검색")
    @GetMapping("/search/{keyword}/{pageNum}")
    public Page<ArtDto> searchArt(@PathVariable String keyword, @PathVariable int pageNum){
        return artService.searchArt(keyword, pageNum);
    }

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/reply")
    public void saveReply(HttpServletRequest request, @RequestBody ReplyDto replyDto){
        artService.saveReply(request, replyDto);
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/reply")
    public void updateReply(HttpServletRequest request, @RequestBody ReplyDto replyDto){
        artService.updateReply(request, replyDto);
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/reply/{replyNo}")
    public void deleteReply(HttpServletRequest request, @PathVariable Long replyNo){
        artService.deleteReply(request, replyNo);
    }




}
