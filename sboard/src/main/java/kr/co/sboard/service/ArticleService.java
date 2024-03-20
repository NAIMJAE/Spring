package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.File;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;
    // 별도의 빈 등록 필요
    private final ModelMapper modelMapper;

    // 게시글 등록
    public void insertArticle(ArticleDTO articleDTO){
        // 파일 업로드
        List<FileDTO> files = fileService.fileUpload(articleDTO);

        // 파일 첨부 갯수 초기화
        articleDTO.setFile(files.size());

        // Entity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        // 게시물 내용 저장 (article) 후 entity 객체 반환
        // (JPA save() 메서드는 default로 저장한 Entity를 반환)
        Article savedArticle = articleRepository.save(article);
        log.info("insertArticle : " + savedArticle.toString());

        // 파일 내용 저장 (file)
        for(FileDTO fileDTO : files){
            fileDTO.setAno(savedArticle.getNo());

            // 여기서 에러나는데 RootConfig 파일에 ModelMapper 설정에 이거 추가 -> .setMatchingStrategy(MatchingStrategies.STRICT)
            File file = modelMapper.map(fileDTO, File.class);
            fileRepository.save(file);
        }
    }

    // 게시물 조회 (/article/view)
    public ArticleDTO selectArticle(int no){
        Optional<Article> result = articleRepository.findById(no);

        ArticleDTO articleDTO = null;

        if(result.isPresent()){
            Article article = result.get();
            articleDTO = modelMapper.map(article, ArticleDTO.class);
        }
        return articleDTO;
    }

    public void updateArticle(ArticleDTO articleDTO){

    }

    public void deleteArticle(int no){

    }

    // 게시판별 게시글 조회
    public PageResponseDTO selectArticleForCate(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");

        // Repository 메서드에 Pageable 객체 전달하여 페이징 처리된 결과 가져오기
        Page<Article> pageArticle = articleRepository.findByCateAndParent(pageRequestDTO.getCate(), 0, pageable);


        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                                    .map(entity -> modelMapper.map(entity, ArticleDTO.class))
                                    .toList();

        int total = (int) pageArticle.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    // 게시글 개수 조회
    public int selectArticleCount(String cate){
        return articleRepository.countByCateAndParent(cate, 0);
    }

    // 첨부 파일 조회
    public List<FileDTO> selectFiles(int ano){
        List<File> files = fileRepository.findByAno(ano);

        List<FileDTO> fileDTOs = new ArrayList<>();
        for(File file : files) {
            fileDTOs.add(modelMapper.map(file, FileDTO.class));
        }
        return fileDTOs;
    }

    // 댓글 등록
    public ResponseEntity<?> insertComment(ArticleDTO articleDTO){

        // Entity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);

        // (JPA save() 메서드는 default로 저장한 Entity를 반환)
        Article savedArticle = articleRepository.save(article);
        log.info("insertArticle : " + savedArticle.toString());

        ArticleDTO result = modelMapper.map(savedArticle, ArticleDTO.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }







}
