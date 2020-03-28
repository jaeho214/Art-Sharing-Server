package kr.ac.skuniv.artsharing.controller;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDetailDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.Art;
import kr.ac.skuniv.artsharing.service.art.ArtDeleteService;
import kr.ac.skuniv.artsharing.service.art.ArtGetService;
import kr.ac.skuniv.artsharing.service.art.ArtSaveService;
import kr.ac.skuniv.artsharing.service.art.ArtUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/artSharing/art")
@RequiredArgsConstructor
public class ArtController {

    private final ArtSaveService artSaveService;
    private final ArtUpdateService artUpdateService;
    private final ArtGetService artGetService;
    private final ArtDeleteService artDeleteService;


    @ApiOperation(value = "작품, 이미지 같이 저장")
    @PostMapping
    public ResponseEntity saveArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                  @RequestPart MultipartFile imageFile,
                                  @RequestParam String json) throws Exception {
        ArtGetDto savedArt = artSaveService.saveArtAndFile(cookie, json, imageFile);
        return ResponseEntity.created(URI.create("artSharing/art/" + savedArt.getId())).body(savedArt);
    }

    @ApiOperation(value = "작품 정보 수정")
    @PutMapping
    public ResponseEntity updateArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                    @RequestPart(required = false) MultipartFile imageFile,
                                    @RequestParam String json) throws IOException {

        return ResponseEntity.ok(artUpdateService.updateArt(imageFile, cookie, json));
    }

    @ApiOperation(value = "작품 삭제")
    @DeleteMapping("/{artNo}")
    public ResponseEntity deleteArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                    @PathVariable Long artNo){
        return artDeleteService.deleteArt(cookie, artNo);
    }

    @ApiOperation(value = "모든 작품 조회")
    @GetMapping("/artsList/{pageNum}")
    public Page<ArtGetDto> getAllArts(@PathVariable int pageNum){
        return artGetService.getAllArts(pageNum);
    }

    @ApiOperation(value = "로그인 되어 있는 작가가 자기 작품 조회")
    @GetMapping("/{pageNum}")
    public Page<ArtGetDto> getArtsByUserId(@CookieValue(value = "user", required = false) Cookie cookie,
                                           @PathVariable int pageNum){
        return artGetService.getArtsByUserId(cookie,pageNum);
    }

    @ApiOperation(value = "작가 보기에서 작가 이름을 클릭하여 작가의 작품 조회")
    @GetMapping("/{artistId}/{pageNum}")
    public Page<ArtGetDto> getArtsByArtist(@PathVariable String artistId,
                                           @PathVariable int pageNum){
        return artGetService.getArtsByArtist(artistId, pageNum);
    }

    @ApiOperation(value = "작품 상세보기")
    @GetMapping("/detail/{artNo}")
    public ArtGetDetailDto getArtDetail(@PathVariable Long artNo){
        return artGetService.getArtDetail(artNo);
    }

    @ApiOperation(value = "작품명 및 작가이름 검색")
    @GetMapping("/search/{keyword}/{pageNum}")
    public Page<ArtGetDto> searchArt(@PathVariable String keyword,
                                     @PathVariable int pageNum){
        return artGetService.searchArt(keyword, pageNum);
    }


    @ApiOperation(value = "사진 상세보기")
    @GetMapping(value = "/image/{artNo}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Long artNo) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(artGetService.getImageResource(artNo));
    }



}
