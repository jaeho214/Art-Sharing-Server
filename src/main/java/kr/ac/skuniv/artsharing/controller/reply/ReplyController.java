package kr.ac.skuniv.artsharing.controller.reply;

import io.swagger.annotations.ApiOperation;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyGetDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplySaveDto;
import kr.ac.skuniv.artsharing.domain.dto.reply.ReplyUpdateDto;
import kr.ac.skuniv.artsharing.service.reply.ReplyDeleteService;
import kr.ac.skuniv.artsharing.service.reply.ReplySaveService;
import kr.ac.skuniv.artsharing.service.reply.ReplyUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.net.URI;

@RequestMapping("/artSharing/art")
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplySaveService replySaveService;
    private final ReplyUpdateService replyUpdateService;
    private final ReplyDeleteService replyDeleteService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/reply")
    public ResponseEntity saveReply(@CookieValue(value = "user", required = false) Cookie cookie,
                                    @RequestBody ReplySaveDto replySaveDto){
        ReplyGetDto savedReply = replySaveService.saveReply(cookie, replySaveDto);
        return ResponseEntity.created(URI.create("/artSharing/art/reply/"+savedReply.getId())).body(savedReply);
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/reply")
    public ResponseEntity updateReply(@CookieValue(value = "user", required = false) Cookie cookie,
                                      @RequestBody ReplyUpdateDto replyUpdateDto){
        return ResponseEntity.ok().body(replyUpdateService.updateReply(cookie, replyUpdateDto));
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/reply/{replyNo}")
    public ResponseEntity deleteReply(@CookieValue(value = "user", required = false) Cookie cookie,
                                      @PathVariable Long replyNo){
        return replyDeleteService.deleteReply(cookie, replyNo);
    }
}
