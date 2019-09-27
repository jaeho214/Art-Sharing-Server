package kr.ac.skuniv.artsharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.artsharing.domain.dto.ReplyDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.service.art.ArtDeleteService;
import kr.ac.skuniv.artsharing.service.art.ArtGetService;
import kr.ac.skuniv.artsharing.service.art.ArtSaveService;
import kr.ac.skuniv.artsharing.service.art.ArtUpdateService;
import kr.ac.skuniv.artsharing.service.art.reply.ReplyDeleteService;
import kr.ac.skuniv.artsharing.service.art.reply.ReplySaveService;
import kr.ac.skuniv.artsharing.service.art.reply.ReplyUpdateService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("artSharing/art")
public class ArtController {

    private ArtSaveService artSaveService;
    private ArtUpdateService artUpdateService;
    private ArtGetService artGetService;
    private ArtDeleteService artDeleteService;
    private ReplySaveService replySaveService;
    private ReplyUpdateService replyUpdateService;
    private ReplyDeleteService replyDeleteService;
    private ObjectMapper objectMapper;

    public ArtController(ArtSaveService artSaveService, ArtUpdateService artUpdateService,
                         ArtGetService artGetService, ArtDeleteService artDeleteService,
                         ReplySaveService replySaveService, ReplyUpdateService replyUpdateService,
                         ReplyDeleteService replyDeleteService, ObjectMapper objectMapper) {
        this.artSaveService = artSaveService;
        this.artUpdateService = artUpdateService;
        this.artGetService = artGetService;
        this.artDeleteService = artDeleteService;
        this.replySaveService = replySaveService;
        this.replyUpdateService = replyUpdateService;
        this.replyDeleteService = replyDeleteService;
        this.objectMapper = objectMapper;
    }

    //    @ApiOperation(value = "작품 등록")
//    @PostMapping
//    public void saveArt(HttpServletRequest request, @RequestBody ArtSaveDto artSaveDto) {
//        artService.saveArt(request, artSaveDto);
//    }

    @ApiOperation(value = "작품, 이미지 같이 저장")
    @PostMapping
    public ResponseEntity<Art> saveArt(HttpServletRequest request,
                                       @RequestPart MultipartFile imageFile,
                                       @RequestParam String json) throws Exception {
        ArtSaveDto artSave = objectMapper.readValue(json, ArtSaveDto.class);
        Art art = artSaveService.saveArtAndFile(request, artSave, imageFile);
        return ResponseEntity.ok(art);
    }

    @ApiOperation(value = "작품 정보 수정")
    @PutMapping
    public ResponseEntity<Art> updateArt(HttpServletRequest request,
                          @RequestPart(required = false) MultipartFile imageFile,
                          @RequestParam String json) throws IOException {
        ArtUpdateDto artUpdate = objectMapper.readValue(json, ArtUpdateDto.class);
        return ResponseEntity.ok(artUpdateService.updateArt(imageFile, request, artUpdate));
    }

    @ApiOperation(value = "작품 삭제")
    @DeleteMapping("/{artNo}")
    public ResponseEntity<Art> deleteArt(HttpServletRequest request,@PathVariable Long artNo){
        return ResponseEntity.ok(artDeleteService.deleteArt(request, artNo));
    }

    @ApiOperation(value = "모든 작품 조회")
    @GetMapping("/artsList/{pageNum}")
    public Page<ArtGetDto> getAllArts(@PathVariable int pageNum){
        return artGetService.getAllArts(pageNum);
    }

    @ApiOperation(value = "작가의 작품 조회")
    @GetMapping("/{pageNum}")
    public Page<ArtGetDto> getArtsByUserId(HttpServletRequest request, @PathVariable int pageNum){
        return artGetService.getArtsByUserId(request,pageNum);
    }

    @ApiOperation(value = "작품 상세보기")
    @GetMapping("/detail/{artNo}")
    public ArtGetDetailDto getArtDetail(@PathVariable Long artNo){
        return artGetService.getArtDetail(artNo);
    }

    @ApiOperation(value = "작품명 및 작가이름 검색")
    @GetMapping("/search/{keyword}/{pageNum}")
    public Page<ArtGetDto> searchArt(@PathVariable String keyword, @PathVariable int pageNum){
        return artGetService.searchArt(keyword, pageNum);
    }

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/reply")
    public void saveReply(HttpServletRequest request, @RequestBody ReplyDto replyDto){
        replySaveService.saveReply(request, replyDto);
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/reply")
    public void updateReply(HttpServletRequest request, @RequestBody ReplyDto replyDto){
        replyUpdateService.updateReply(request, replyDto);
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/reply/{replyNo}")
    public void deleteReply(HttpServletRequest request, @PathVariable Long replyNo){
        replyDeleteService.deleteReply(request, replyNo);
    }

    @ApiOperation(value = "사진 상세보기")
    @GetMapping(value = "/image/{artNo}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Long artNo) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(artGetService.getImageResource(artNo));
    }



}
