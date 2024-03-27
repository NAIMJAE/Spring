package kr.co.sboard.controller;

import kr.co.sboard.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    @GetMapping("/file/fileDownload")
    public ResponseEntity<?> fileDownload(int fno) {
        log.info("fileDownload : " + fno);
        return fileService.fileDownload(fno);
    }

    @GetMapping("/file/downloadCount/{fno}")
    public ResponseEntity<?> fileDownloadCount(@PathVariable("fno") int fno) {
        log.info("fileDownloadCount : " + fno);
        return fileService.fileDownloadCount(fno);
    }

    @GetMapping("/file/delete/{fno}")
    public void fileDelete(@PathVariable("fno") int fno) {
        log.info("fileDelete : " + fno);
        fileService.fileDelete(fno);
    }

    // 프로필 사진 업로드
    @PostMapping("/file/uploadProfile")
    public ResponseEntity<?> uploadProfile(@RequestParam("file") MultipartFile file, @RequestParam("uid") String uid) {
        String sName = fileService.saveProfile(file, uid);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", sName);
        return ResponseEntity.ok().body(resultMap);
    }
}