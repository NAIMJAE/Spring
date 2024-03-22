package kr.co.sboard.service;

import jakarta.transaction.Transactional;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.entity.Article;
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
import java.util.*;

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
        log.info("파일 업로드 service1 시작");

        // uploads 폴더 자동 생성
        File file = new File(fileUploadPath);
        if(!file.exists()){
            file.mkdir();
        }

        // 파일 업로드 시스템 경로 구하기
        String path = file.getAbsolutePath();
        log.info("파일 업로드 service2 파일 저장 시작");

        // 저장
        List<FileDTO> files = new ArrayList<>();

        for(MultipartFile mf : articleDTO.getFiles()){
            log.info("파일 업로드 service3 파일 저장 for문 시작");
            // 첨부파일이 없으면 에러발생 / null 체크




            if (!mf.isEmpty()){
                log.info("파일 업로드 service4 파일이 null이 아닐때");

                // oName, sName 구하기
                String oName = mf.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                log.info("파일 업로드 service5 oName : " + oName);
                log.info("파일 업로드 service6 sName : " + sName);

                try {
                    // mf를 path경로에 sName로 생성
                    mf.transferTo(new File(path, sName));
                    // 파일 정보 생성
                    FileDTO fileDTO = FileDTO.builder()
                            .oName(oName)
                            .sName(sName)
                            .build();
                    files.add(fileDTO);
                    log.info("파일 업로드 service7 저장 끝 fileDTO : " + fileDTO.toString());

                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        log.info("파일 업로드 service8 끝");
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

    public ResponseEntity<?> fileDownloadCount(int fno)  {
        log.info("fileDownloadCount");
        // 파일 조회
        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();

        // 파일 다운로드 버튼을 누르면 먼저 보내진 요청으로 인해 다운로드 카운트 +1
        // 새로 업데이트된 다운로드 카운트 조회해서 보내기
        
        // 다운로드 카운트 Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("count", file.getDownload());

        return ResponseEntity.ok().body(resultMap);
    }
    
    // 파일 삭제
    @Transactional
    public void fileDelete(int fno){
        log.info("파일 삭제 service1 시작");
        // 삭제 전 조회
        Optional<kr.co.sboard.entity.File> Optfile = fileRepository.findById(fno);

        log.info("파일 삭제 service2 Optfile : " + Optfile);

        if(Optfile.isPresent()){
            log.info("파일 삭제 service3 Optfile이 null이 아닐때");

            int ano = Optfile.get().getAno();
            log.info("파일 삭제 service4 ano : " + ano);

            String fileName = Optfile.get().getSName();
            log.info("파일 삭제 service5 fileName : " + fileName);

            // 디비 삭제
            fileRepository.deleteById(fno);

            // uploads의 파일 삭제
            log.info("파일 삭제 service11 uploads 파일 삭제");
            File deleteFile = new File(fileUploadPath, fileName);

            if (deleteFile.delete()){
                log.info("파일 삭제 service11 파일 삭제 성공");
            }else{
                log.info("파일 삭제 service11 파일 삭제 실패");
            }
        }
        log.info("파일 삭제 service12 끝");
    }
}
