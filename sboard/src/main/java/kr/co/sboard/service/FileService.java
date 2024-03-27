package kr.co.sboard.service;

import jakarta.transaction.Transactional;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.User;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.FileRepository;
import kr.co.sboard.repository.UserRepository;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    private final UserRepository userRepository;

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

    /*
        ㅁ 게시글의 첨부파일처럼 파일 업로드 방식을 사용할 경우
          1. 파일 업로드 방식으로 프로필 사진을 전달하면 JS로 동적인 처리시 딜레이 발생 (IntelliJ 문제)

          2. 배포 단계에서는 resources에 접근할 수 없어 이미지 저장 불가능

          3. 그럼 배포 단계에서 외부 디렉토리에 이미지 파일을 저장하고 외부에서 불러오는 방식 사용해야 함

        ㅁ 프로필 사진을 바이너리 코드로 변환 후 DB에 저장
          1. file 객체의 getBytes() 메서드를 이용해 파일의 내용을 바이트 배열로 읽음
             - 이진 데이터는 효율적인 처리를 위해 배열 구조로 구성됨
             - "hello" => [104, 101, 108, 108, 111]

          2. encodeToString() 메서드를 이용해 Base64로 인코딩
             - Base64 인코딩은 이진 데이터를 텍스트 형식으로 변환하는 방법 중 하나
             - 이진 데이터는 특수문자나 제어 문자를 포함하고 있어 변환이 필요
             - Base64는 64개의 문자로 이루어진 문자 집합을 사용하여 이진 데이터를 텍스트로 표현

          3. 실제로 DB에 저장될 이미지 파일 문자열 생성
             - Base64로 인코딩된 문자열에 data:image/gif;base64,를 더해 새로운 문자열 생성
             - HTML 또는 CSS에서 바이너리 코드로 인코딩된 이미지를 불러오기 위함
             - data:image/gif;base64, 는 인코딩된 이미지를 나타내는 데이터 URI의 시작 부분

          4. uid로 user Entity를 조회하고 profile에 변환된 이미지 파일 문자열 update
          
          5. 바이너리 코드로 변환된 이미지 파일 문자열을 반환하고 JS를 통해 동적으로 이미지 생성

        ㅁ 선행 작업
          1. 이미지 파일 문자열이 저장될 칼럼의 데이터 유형을 BLOB로 설정
             - 대체로 BLOB 유형은 이진 데이터를 저장하는데 사용되는 데이터 유형
             - BLOB 유형은 대용량 데이터를 저장할 수 있음
             
          2. Entity에서도 @BLOB 어노테이션 선언
         
         ※ 성능이 괜찮을지 고민    
         ※ 간단한 이미지 파일 처리를 위한 Thumbnail 라이브러리도 있다고 함 (fileupload 방식)
     */
    // 프로필 사진 저장
    public String saveProfile(MultipartFile file, String uid){
        try {
            // 바이너리 코드로 인코딩
            byte[] fileBytes = file.getBytes();
            String encodeFile = Base64.getEncoder().encodeToString(fileBytes); //data:image/gif;base64,
            String sName = "data:image/gif;base64," + encodeFile;

            Optional<User> optUser = userRepository.findById(uid);
            if (optUser.isPresent()){
                optUser.get().setProfile(sName);
                userRepository.save(optUser.get());
            }
            return sName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
