package kr.ac.skuniv.artsharing.controller.art;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
import kr.ac.skuniv.artsharing.service.art.ArtDeleteService;
import kr.ac.skuniv.artsharing.service.art.ArtGetService;
import kr.ac.skuniv.artsharing.service.art.ArtSaveService;
import kr.ac.skuniv.artsharing.service.art.ArtUpdateService;
import lombok.RequiredArgsConstructor;
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
                                  @RequestBody ArtSaveDto artSaveDto){
        ArtGetDto savedArt = artSaveService.saveArt(cookie, artSaveDto);
        return ResponseEntity.created(URI.create("artSharing/art/" + savedArt.getId())).body(savedArt);
    }

    @ApiOperation(value = "작품 정보 수정")
    @PostMapping("/update")
    public ResponseEntity updateArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                    @RequestBody ArtUpdateDto artUpdateDto) {

        return ResponseEntity.ok(artUpdateService.updateArt(cookie, artUpdateDto));
    }

    @ApiOperation(value = "작품 삭제")
    @DeleteMapping("/{art_id}")
    public ResponseEntity deleteArt(@CookieValue(value = "user", required = false) Cookie cookie,
                                    @PathVariable Long art_id){
        return artDeleteService.deleteArt(cookie, art_id);
    }

    @ApiOperation(value = "모든 작품 조회")
    @GetMapping("/list")
    public ResponseEntity getAllArts(@RequestParam("pageNo") int pageNo){
        return ResponseEntity.ok().body(artGetService.getAllArts(pageNo));
    }

    @ApiOperation(value = "로그인 되어 있는 작가가 자기 작품 조회")
    @GetMapping
    public ResponseEntity getArtsByUserId(@CookieValue(value = "user", required = false) Cookie cookie,
                                          @RequestParam("pageNo") int pageNo){
        return ResponseEntity.ok().body(artGetService.getArtsByUserId(cookie, pageNo));
    }

    @ApiOperation(value = "작가 보기에서 작가 이름을 클릭하여 작가의 작품 조회")
    @GetMapping("/{artistId}")
    public ResponseEntity getArtsByArtist(@PathVariable String artistId,
                                           @RequestParam("pageNo") int pageNo){
        return ResponseEntity.ok().body(artGetService.getArtsByArtist(artistId, pageNo));
    }

    @ApiOperation(value = "작품 상세보기")
    @GetMapping("/detail/{art_id}")
    public ResponseEntity getArtDetail(@PathVariable Long art_id){
        return ResponseEntity.ok().body(artGetService.getArtDetail(art_id));
    }

    @ApiOperation(value = "작품명 및 작가이름 검색")
    @GetMapping("/search/{keyword}")
    public ResponseEntity searchArtByKeyword(@PathVariable String keyword,
                                             @RequestParam("pageNo") int pageNo){
        return ResponseEntity.ok().body(artGetService.searchArtByKeyword(keyword, pageNo));
    }

    @ApiOperation(value = "대여 가능한 작품들 보기")
    @GetMapping("/rent")
    public ResponseEntity getArtByRent(@RequestParam("pageNo") int pageNo){
        return ResponseEntity.ok().body(artGetService.getArtByRent(pageNo));
    }

}
