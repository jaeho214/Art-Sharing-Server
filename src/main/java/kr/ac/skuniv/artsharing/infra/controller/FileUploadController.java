package kr.ac.skuniv.artsharing.infra.controller;


import kr.ac.skuniv.artsharing.infra.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/artSharing/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Uploader s3Uploader;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile multipartFile){
        String fileName = UUID.randomUUID().toString();
        return ResponseEntity.ok(s3Uploader.uploadFile(multipartFile, fileName));
    }
}
