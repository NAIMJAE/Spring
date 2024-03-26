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
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("uploadProfile");
        if (!file.isEmpty()) {
            try {
                // 파일 저장 경로 설정
                String uploadDir = "src/main/resources/static/images";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 파일 이름 생성
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                // 파일 저장
                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(file.getBytes());
                stream.flush();
                stream.close();

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("result", "File uploaded successfully: " + fileName);
                return ResponseEntity.ok().body(resultMap);
            } catch (Exception e) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("result", "Failed to upload file: " + e.getMessage());
                return ResponseEntity.ok().body(resultMap);
            }
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", "No file uploaded");
            return ResponseEntity.ok().body(resultMap);
        }
    }

}