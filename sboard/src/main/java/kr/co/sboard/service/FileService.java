package kr.co.sboard.service;

import jakarta.transaction.Transactional;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    // 첨부 파일 업로드
    public List<FileDTO> fileUpload(ArticleDTO articleDTO){
        log.info("fileUpload");
        // 파일 업로드 시스템 경로 구하기
        String path = new File(fileUploadPath).getAbsolutePath();
        // 저장
        List<FileDTO> files = new ArrayList<>();

        for(MultipartFile mf : articleDTO.getFiles()){
            log.info("fileUpload - for문");
            // 첨부파일이 없으면 에러발생 / null 체크
            if (!mf.isEmpty()){
                log.info("fileUpload - if문");
                // oName, sName 구하기
                String oName = mf.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;
                
                log.info("oName : " + oName);
                log.info("sName : " + sName);
                
                try {
                    log.info("fileUpload - try");
                    // mf를 path경로에 sName로 생성
                    mf.transferTo(new File(path, sName));
                    // 파일 정보 생성
                    FileDTO fileDTO = FileDTO.builder()
                            .oName(oName)
                            .sName(sName)
                            .build();
                    files.add(fileDTO);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return files;
    }
    @Transactional
    public ResponseEntity<?> fileDownload(int fno) {
        // 파일 조회
        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();
        try {
            Path path = Paths.get(fileUploadPath + file.getSName());

            String contentType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition
                    (ContentDisposition.builder("attachment")
                            .filename(file.getOName(), StandardCharsets.UTF_8).build());
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            Resource resource = new InputStreamResource(Files.newInputStream(path));

            // 파일 다운로드 카운터
            file.setDownload(file.getDownload() + 1);
            fileRepository.save(file);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }catch (IOException e){
            log.error("fileDownload : " + e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }
}
